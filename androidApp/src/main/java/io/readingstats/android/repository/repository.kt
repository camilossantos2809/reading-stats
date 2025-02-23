package io.readingstats.android.repository

import io.readingstats.android.domain.Book
import io.readingstats.android.domain.NewReadingProgress
import io.readingstats.android.domain.ReadingProgress
import io.readingstats.android.services.db.connect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Repository {
    fun getAllBooks(): List<Book> {
        connect().use {
            return it.query("select * from book").map { row ->
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
    }

    fun getReadingProgressByBookId(bookId: String): List<ReadingProgress> {
        connect().use {
            return it.query(
                """
                    select 
                      book_id, date_read, progress, 
                      progress_previous, pages_read, id
                    from book_reading_progress
                    where book_id = $bookId
                    order by date_read desc;
                    """
            )
                .map { row ->
                    ReadingProgress(
                        bookId = row[0] as String,
                        dateRead = row[1] as String,
                        lastPage = row[2] as Long,
                        initialPage = row[3] as Long,
                        pagesRead = row[4] as Long,
                        id = row[5] as Long
                    )
                }
        }
    }

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