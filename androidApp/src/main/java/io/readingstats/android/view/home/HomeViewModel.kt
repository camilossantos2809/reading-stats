package io.readingstats.android.view.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.readingstats.android.domain.Book
import io.readingstats.android.domain.Goal
import io.readingstats.android.repository.Repository
import io.readingstats.android.view.SharedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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
                _books.value = Repository.getAllBooks()
            } catch (e: Exception) {
                Log.w("turso", "Error testing turso", e)
            }
        }
    }

    fun selectBook(book: Book) {
        SharedState.updateBook(book)
    }

}