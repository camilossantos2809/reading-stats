package io.readingstats.android.view.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.readingstats.android.domain.ReadingProgress
import io.readingstats.android.services.db.connect
import io.readingstats.android.view.SharedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BookViewModel(val bookId: String?) : ViewModel() {
    val book = SharedState.book
    val readingProgress = SharedState.readingProgress
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun fetchBook() {
        _loading.value = true
        viewModelScope.launch {
            try {
                connect().use {
                    val result = it.query(
                        """
                        select 
                          book_id, date_read, progress, 
                          progress_previous, pages_read
                        from book_reading_progress
                        where book_id = $bookId
                        order by date_read desc;
                        """
                    )
                        .map { row ->
                            ReadingProgress(
                                bookId = row[0] as String,
                                dateRead = row[1] as String,
                                lastPage = row[2] as Long,
                                initialPage = row[3] as Long,
                                pagesRead = row[4] as Long
                            )
                        }
                    SharedState.updateReadingProgress(result)
                }
            } catch (e: Exception) {
                Log.w("readingStats", "Error fetching book $bookId.", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteProgress(dateRead: String,) {
        _loading.value = true
        if (bookId.isNullOrEmpty()) {
            Log.w("readingStats", "Book id is required to save progress")
            return
        }
        viewModelScope.launch {
            try {
//                val db = Firebase.firestore
//                val bookRef = db.collection("books").document(bookId)
//                val readingProgressRef =
//                    db.collection("readingProgress").document(readingProgressId)
//                bookRef.update("readingProgress", FieldValue.arrayRemove(readingProgressRef))
//                    .await()
//                readingProgressRef.delete().await()
//                fetchBook()
            } catch (e: Exception) {
                Log.w("readingStats", "Error deleting reading progress $dateRead.", e)
            } finally {
                _loading.value = false
            }
        }
    }
}