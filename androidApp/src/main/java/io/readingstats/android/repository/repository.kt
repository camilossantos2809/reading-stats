package io.readingstats.android.repository

import io.readingstats.android.domain.NewReadingProgress
import io.readingstats.android.services.db.connect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Repository {
    fun saveReadingProgress(progress: NewReadingProgress) {
        val dateRead: String =
            LocalDate.parse(progress.dateRead, DateTimeFormatter.ofPattern("ddMMyyyy"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        connect().use {
            it.execute(
                """
                INSERT INTO book_reading_progress (book_id, date_read, progress, progress_previous, pages_read)
                  VALUES (:book_id, :date_read, :progress, :progress_previous, :pages_read)
                """,
                mapOf(
                    "book_id" to progress.bookId,
                    "date_read" to dateRead,
                    "progress" to progress.lastPage,
                    "progress_previous" to progress.initialPage,
                    "pages_read" to progress.pagesRead
                )
            )
        }
    }
}