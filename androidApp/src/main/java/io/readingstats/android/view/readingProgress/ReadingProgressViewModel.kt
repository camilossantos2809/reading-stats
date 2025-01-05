package io.readingstats.android.view.readingProgress

import androidx.lifecycle.ViewModel
import io.readingstats.android.domain.ReadingProgress
import io.readingstats.android.domain.toTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun saveProgress() {
        _formData.value = _formData.value.copy(errorMessage = null)
        val lastPageInt = _formData.value.lastPage.toIntOrNull()
        if (_formData.value.lastPage.isEmpty() || lastPageInt == null) {
            _formData.value =
                _formData.value.copy(errorMessage = "Last page should be a number and not empty")
            return
        }
        val progress = ReadingProgress(
            date = _formData.value.date.toTimestamp(),
            lastPage = _formData.value.lastPage.toInt()
        )
    }

    fun updateDate(date: String) {
        if (date.length <= maxChar) {
            _formData.value = _formData.value.copy(date = date)
        }
    }

    fun updateLastPage(lastPage: String) {
        _formData.value = _formData.value.copy(lastPage = lastPage)
    }
}