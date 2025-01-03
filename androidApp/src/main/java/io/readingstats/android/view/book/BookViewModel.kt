package io.readingstats.android.view.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import io.readingstats.android.domain.Book
import io.readingstats.android.domain.ReadingProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter
import java.time.ZoneId

fun Timestamp.formatToDate(): String {
    val instant = this.toDate().toInstant()
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return formatter.format(localDate)
}

class BookViewModel(val bookId: String?) : ViewModel() {
    private val _book = MutableStateFlow(Book())
    val book get() = _book.asStateFlow()

    private val _readingProgress = MutableStateFlow(listOf<ReadingProgress>())
    val readingProgress get() = _readingProgress.asStateFlow()

    init {
        fetchBook()
    }

    private fun fetchBook() {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val bookRef = db.collection("books").document(bookId ?: "")
                val bookSnapshot = bookRef.get().await()
                val result = bookSnapshot.toObject<Book>()
                if (result != null) {
                    _book.value = result.copy(id = bookRef.id)

                    val readingProgressList = result.readingProgress?.mapNotNull { ref ->
                        try {
                            val readingProgress = ref.get().await().toObject<ReadingProgress>()
                            readingProgress?.copy(id = ref.id)
                        } catch (e: Exception) {
                            Log.w("readingStats", "Failed to fetch reading progress: ${ref.id}", e)
                            null
                        }
                    }.orEmpty()
                    _readingProgress.value = readingProgressList.sortedByDescending { it.lastPage }
                }
            } catch (e: Exception) {
                Log.w("readingStats", "Error fetching book $bookId.", e)
            }
        }
    }
}