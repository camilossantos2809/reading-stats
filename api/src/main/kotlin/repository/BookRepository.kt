package io.repository

import io.db.BookGoalTable
import io.db.BookReadingProgressTable
import io.db.BookTable
import io.db.GoalTable
import io.model.*
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.core.statements.StatementType
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun getReadingBooks(): List<ReadingBook>
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

    override suspend fun getReadingBooks() = suspendTransaction {
        exec(
            """
            with progress as (
              select 
                book_id,
                max(current_page) as current_page
              from book_reading_progress
              group by book_id
            )
            select
              b.id,
              b.isbn,
              b.name,
              b.author,
              b.pages,
              b.asin ,
              p.current_page 
            from book b 
              inner join book_goal bg on b.id = bg.book_id 
              left join progress p on b.id = p.book_id
            where bg.status = 'reading';
            """,
            explicitStatementType = StatementType.SELECT
        ) { result ->
            val info = mutableListOf<ReadingBook>()
            while (result.next()) {
                info += ReadingBook(
                    book = Book(
                        id = result.getInt(1),
                        isbn = result.getString(2),
                        name = result.getString(3),
                        author = result.getString(4),
                        pages = result.getInt(5),
                        asin = result.getString(6)
                    ), currentPage = result.getInt(7)
                )
            }
            info
        } ?: emptyList()
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
            if (ex.message?.contains("UNIQUE constraint failed") == true) {
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
                BookGoalTable, JoinType.INNER, BookReadingProgressTable.bookId, BookGoalTable.bookId
            ).join(
                GoalTable, JoinType.INNER, BookGoalTable.goalId, GoalTable.id
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
                BookReadingProgressTable.currentPage to SortOrder.DESC, BookReadingProgressTable.id to SortOrder.DESC
            ).map {
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

    override suspend fun getReadingBooks(): List<ReadingBook> {
        TODO("Not yet implemented")
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