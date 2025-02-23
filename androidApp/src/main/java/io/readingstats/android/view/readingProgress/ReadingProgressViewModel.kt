package io.readingstats.android.view.readingProgress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.readingstats.android.domain.ReadingProgress
import io.readingstats.android.repository.Repository
import io.readingstats.android.view.SharedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val maxChar = 8

val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"))

data class FormData(
    val date: String = currentDate,
    val lastPage: String = "",
    val errorMessage: String? = null
)

class ReadingProgressViewModel : ViewModel() {
    private val _formData = MutableStateFlow(FormData())
    val formData get() = _formData.asStateFlow()
    private val readingProgress = SharedState.readingProgress

    fun saveProgress(bookId: String?, navController: NavController) {
        clearErrorMessage()
        val lastPageNumber = _formData.value.lastPage.toLongOrNull()
        if (_formData.value.lastPage.isEmpty() || lastPageNumber == null) {
            updateErrorMessage("Last page should be a number and not empty")
            return
        }
        if (bookId.isNullOrEmpty()) {
            updateErrorMessage("Book id is required to save progress")
            return
        }
        val previousLastPage = if (readingProgress.value.isEmpty()) {
            0
        } else {
            readingProgress.value.first().lastPage
        }

        viewModelScope.launch {
            val progress = ReadingProgress(
                bookId = bookId,
                dateRead = _formData.value.date,
                initialPage = previousLastPage,
                lastPage = lastPageNumber,
                pagesRead = lastPageNumber - previousLastPage
            )
            try {
                Repository.saveReadingProgress(progress)
                _formData.value = FormData()
                navController.popBackStack()
            } catch (e: Exception) {
                updateErrorMessage(e.message)
            }
        }
    }

    fun updateDate(date: String) {
        if (date.length <= maxChar) {
            _formData.value = _formData.value.copy(date = date)
        }
    }

    fun updateLastPage(lastPage: String) {
        _formData.value = _formData.value.copy(lastPage = lastPage)
    }

    private fun updateErrorMessage(message: String?) {
        _formData.value = _formData.value.copy(errorMessage = message)
    }

    private fun clearErrorMessage() {
        _formData.value = _formData.value.copy(errorMessage = null)
    }

}