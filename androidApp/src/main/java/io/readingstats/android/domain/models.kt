package io.readingstats.android.domain


data class Book(
    val id: String = "",
    val title: String = "",
    val pages: Int = 0,
    val author: String = "",
    val status: String = "to read",
)

data class Goal(
    val pages: Int = 0,
)

data class NewReadingProgress(
    val bookId: String,
    val dateRead: String,
    val initialPage: Long,
    val lastPage: Long,
    val pagesRead: Long
)

data class ReadingProgress(
    val id: Long,
    val bookId: String,
    val dateRead: String,
    val initialPage: Long = 0,
    val lastPage: Long = 0,
    val pagesRead: Long = 0
)