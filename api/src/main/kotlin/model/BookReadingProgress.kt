package io.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class BookReadingProgress(
    val id: Int,
    val dateRead: LocalDateTime,
    val progress: Int,
    val progressPrevious: Int = 0,
    val pagesRead: Int = 0
)

@Serializable
data class NewBookReadingProgress(
    val bookId: Int,
    val dateRead: String,
    val lastPageRead: Int
)

@Serializable
data class NewBookReadingProgressBody(
    val dateRead: String,
    val lastPageRead: Int
)

@Serializable
data class GetReadingProgressResponse(
    val book: Book,
    val progress: List<BookReadingProgress>
)