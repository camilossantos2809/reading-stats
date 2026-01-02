package io.repository

import io.db.*
import io.model.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.sum
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update


interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun addBook(book: NewBook)
    suspend fun editBook(book: EditBook)
    suspend fun addReadingProgress(newProgress: NewBookReadingProgress)
    suspend fun getReadingProgress(bookId: Int): GetReadingProgressResponse?
    suspend fun deleteReadingProgress(readingProgressId: Int)
}


object BookRepositorySQLite : BookRepository {

    private fun ResultRow.toBook() = Book(
        id = this[BookTable.id],
        isbn = this[BookTable.isbn],
        name = this[BookTable.name],
        author = this[BookTable.author],
        pages = this[BookTable.pages]
    )


    override suspend fun getAllBooks() = transaction {
        BookTable.selectAll().map { it.toBook() }
    }

    override suspend fun addBook(book: NewBook) {
        try {
            suspendTransaction {
                BookTable.insert {
                    it[isbn] = book.isbn
                    it[name] = book.name
                    it[author] = book.author
                    it[pages] = book.pages
                }
            }
        } catch (ex: ExposedSQLException) {
            if (
                ex.message?.contains("UNIQUE constraint failed") == true
            ) {
                throw BookAlreadyExistsException("A book with ISBN '${book.isbn}' already exists.")
            }
            throw BookRepositoryException("Database error: ${ex.message}")
        }

    }

    override suspend fun editBook(book: EditBook) {
        suspendTransaction {
            BookTable.update({ BookTable.id eq book.id }) { row ->
                book.isbn?.let { row[isbn] = it }
                book.name?.let { row[name] = it }
                book.author?.let { row[author] = it }
                book.pages?.let { row[pages] = it }
            }
        }
    }

    override suspend fun addReadingProgress(newProgress: NewBookReadingProgress) {

        suspendTransaction {

            BookReadingProgressTable.insert {
                it[bookId] = newProgress.bookId
                it[recordedAt] = LocalDateTime.parse("${newProgress.dateRead}T00:00:00")
                it[currentPage] = newProgress.lastPageRead
            }

            val bookGoal = BookGoalTable.select(BookGoalTable.goalId).where {
                BookGoalTable.bookId eq newProgress.bookId
            }.firstOrNull()

            if (bookGoal == null) {
                return@suspendTransaction
            }

            val goalPagesRead = BookReadingProgressTable.join(
                BookGoalTable,
                JoinType.INNER,
                BookReadingProgressTable.bookId,
                BookGoalTable.bookId
            ).join(
                GoalTable,
                JoinType.INNER,
                BookGoalTable.goalId,
                GoalTable.id
            ).select(
                BookReadingProgressTable.currentPage.sum()
            ).where {
                GoalTable.id eq bookGoal[BookGoalTable.goalId]
            }

            GoalTable.update({ GoalTable.id eq bookGoal[BookGoalTable.goalId] }) {
                it[pagesRead] = goalPagesRead
            }
        }
    }

    override suspend fun getReadingProgress(bookId: Int): GetReadingProgressResponse? {
        return suspendTransaction {
            val book = BookTable.selectAll().where { BookTable.id eq bookId }.firstOrNull()?.toBook()
            if (book == null) {
                return@suspendTransaction null
            }
            val progress = BookReadingProgressTable.selectAll().where {
                BookReadingProgressTable.bookId eq bookId
            }.orderBy(
                BookReadingProgressTable.currentPage to SortOrder.DESC,
                BookReadingProgressTable.id to SortOrder.DESC
            )
                .map {
                    BookReadingProgress(
                        id = it[BookReadingProgressTable.id],
                        dateRead = it[BookReadingProgressTable.recordedAt],
                        progressPrevious = 0,
                        progress = 0,
                        pagesRead = it[BookReadingProgressTable.currentPage],
                    )
                }

            return@suspendTransaction GetReadingProgressResponse(book = book, progress = progress)
        }
    }

    override suspend fun deleteReadingProgress(readingProgressId: Int) {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            BookReadingProgressTable.deleteWhere { id eq readingProgressId }
        }
    }
}

object BookRepositoryFake : BookRepository {
    override suspend fun getAllBooks(): List<Book> {
        return listOf(
            Book(id = 1, isbn = "1586487981", name = "A Economia dos Pobres", pages = 311),
            Book(id = 2, isbn = "9786559790760", name = "A Arte da Estatística", pages = 464)
        )
    }

    override suspend fun addBook(book: NewBook) {
        TODO("Not yet implemented")
    }

    override suspend fun editBook(book: EditBook) {
        TODO("Not yet implemented")
    }

    override suspend fun addReadingProgress(newProgress: NewBookReadingProgress) {
        TODO("Not yet implemented")
    }

    override suspend fun getReadingProgress(bookId: Int): GetReadingProgressResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReadingProgress(readingProgressId: Int) {
        TODO("Not yet implemented")
    }


}