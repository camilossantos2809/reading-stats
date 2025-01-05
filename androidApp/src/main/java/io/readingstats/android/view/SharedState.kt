package io.readingstats.android.view

import io.readingstats.android.domain.Book
import io.readingstats.android.domain.ReadingProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SharedState {
    private val _readingProgress = MutableStateFlow(listOf<ReadingProgress>())
    val readingProgress get() = _readingProgress.asStateFlow()

    private val _book = MutableStateFlow(Book())
    val book = _book.asStateFlow()

    fun updateReadingProgress(newValue: List<ReadingProgress>) {
        _readingProgress.value = newValue
    }

    fun updateBook(newValue: Book) {
        _book.value = newValue
    }
}