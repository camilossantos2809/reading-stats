package io.readingstats.android.view.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.readingstats.android.domain.Book
import io.readingstats.android.domain.Goal
import io.readingstats.android.services.db.connect
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
                connect().use {
                    _books.value = it.query("select * from book").map { row ->
                        val author = row[2]
                        val pages = row[3] as Long
                        Book(
                            id = row[0] as String,
                            title = row[1] as String,
                            author = if (author == null) "" else author as String,
                            pages = pages.toInt(),
                            status = row[4] as String
                        )
                    }
                }
            } catch (e: Exception) {
                Log.w("turso", "Error testing turso", e)
            }
        }
    }

    fun selectBook(book:Book){
        SharedState.updateBook(book)
    }

}