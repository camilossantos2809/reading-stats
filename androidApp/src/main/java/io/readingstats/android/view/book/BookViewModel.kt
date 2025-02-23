package io.readingstats.android.view.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.readingstats.android.repository.Repository
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
                val result = Repository.getReadingProgressByBookId(bookId ?: "")
                SharedState.updateReadingProgress(result)
            } catch (e: Exception) {
                Log.w("readingStats", "Error fetching book $bookId.", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteProgress(id: Long) {
        _loading.value = true
        if (bookId.isNullOrEmpty()) {
            Log.w("readingStats", "Book id is required to save progress")
            return
        }
        viewModelScope.launch {
            try {
                Repository.deleteReadingProgress(id)
                fetchBook()
            } catch (e: Exception) {
                Log.w("readingStats", "Error deleting reading progress $id.", e)
            } finally {
                _loading.value = false
            }
        }
    }
}