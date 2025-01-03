package io.readingstats.android.view.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class Book(
    val id: String = "",
    val title: String = "",
    val pages: Int = 0,
    val author: String = ""
)

data class Goal(val pages: Int = 0, val books: List<DocumentReference>? = null)

class HomeViewModel : ViewModel() {
    private val _goal = MutableStateFlow(Goal())
    val goal get() = _goal.asStateFlow()

    private val _books = MutableStateFlow(listOf<Book>())
    val books get() = _books.asStateFlow()

    init {
        fetchGoalAndBooks()
    }

    private fun fetchGoalAndBooks() {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val goalRef = db.collection("goals").document("2025")
                val goalSnapshot = goalRef.get().await()
                val result = goalSnapshot.toObject<Goal>()
                if (result != null) {
                    _goal.value = result
                    val bookList = result.books?.mapNotNull { ref ->
                        try {
                            val book = ref.get().await().toObject<Book>()
                            book?.copy(id = ref.id)
                        } catch (e: Exception) {
                            Log.w("readingStats", "Failed to fetch book: ${ref.id}", e)
                            null
                        }
                    }.orEmpty()
                    _books.value = bookList
                }
            } catch (e: Exception) {
                Log.w("readingStats", "Error fetching goal or books.", e)
            }
        }
    }

}