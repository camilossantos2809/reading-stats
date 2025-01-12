package io.readingstats.android.view.readingProgress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import io.readingstats.android.domain.toTimestamp
import io.readingstats.android.view.SharedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    fun saveProgress(bookId: String?,navController: NavController) {
        clearErrorMessage()
        val lastPageInt = _formData.value.lastPage.toIntOrNull()
        if (_formData.value.lastPage.isEmpty() || lastPageInt == null) {
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
            try {
                val db = Firebase.firestore
                // TODO: Criar componente para alterar "goal"
                val goalRef = db.collection("goals").document("2025")
                val bookRef = db.collection("books").document(bookId)
                val progressMap = mapOf(
                    "goal" to goalRef,
                    "book" to bookRef,
                    "initialPage" to previousLastPage,
                    "date" to _formData.value.date.toTimestamp(),
                    "lastPage" to lastPageInt,
                    "pagesRead" to lastPageInt - previousLastPage,
                )
                val readingProgressRef = db.collection("readingProgress").add(progressMap).await()
                bookRef.update("readingProgress", FieldValue.arrayUnion(readingProgressRef)).await()
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