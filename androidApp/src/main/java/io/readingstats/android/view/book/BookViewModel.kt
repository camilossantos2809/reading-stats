package io.readingstats.android.view.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import io.readingstats.android.domain.Book
import io.readingstats.android.domain.ReadingProgress
import io.readingstats.android.view.SharedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class BookViewModel(val bookId: String?) : ViewModel() {
    val book = SharedState.book
    val readingProgress = SharedState.readingProgress
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun fetchBook() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val bookRef = db.collection("books").document(bookId ?: "")
                val bookSnapshot = bookRef.get().await()
                val result = bookSnapshot.toObject<Book>()
                if (result != null) {
                    SharedState.updateBook(result.copy(id = bookRef.id))

                    val readingProgressList = result.readingProgress?.mapNotNull { ref ->
                        try {
                            val readingProgress = ref.get().await().toObject<ReadingProgress>()
                            readingProgress?.copy(id = ref.id)
                        } catch (e: Exception) {
                            Log.w("readingStats", "Failed to fetch reading progress: ${ref.id}", e)
                            null
                        }
                    }.orEmpty()
                    SharedState.updateReadingProgress(readingProgressList.sortedByDescending { it.lastPage })
                }
            } catch (e: Exception) {
                Log.w("readingStats", "Error fetching book $bookId.", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteProgress(readingProgressId: String) {
        _loading.value = true
        if (bookId.isNullOrEmpty()) {
            Log.w("readingStats", "Book id is required to save progress")
            return
        }
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val bookRef = db.collection("books").document(bookId)
                val readingProgressRef =
                    db.collection("readingProgress").document(readingProgressId)
                bookRef.update("readingProgress", FieldValue.arrayRemove(readingProgressRef))
                    .await()
                readingProgressRef.delete().await()
                fetchBook()
            } catch (e: Exception) {
                Log.w("readingStats", "Error deleting reading progress $readingProgressId.", e)
            } finally {
                _loading.value = false
            }
        }
    }
}