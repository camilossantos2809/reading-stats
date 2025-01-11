package io.readingstats.android.view.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import io.readingstats.android.domain.Book
import io.readingstats.android.domain.ReadingProgress
import io.readingstats.android.view.SharedState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class BookViewModel(val bookId: String?) : ViewModel() {
    val book = SharedState.book
    val readingProgress = SharedState.readingProgress

    fun fetchBook() {
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
            }
        }
    }
}