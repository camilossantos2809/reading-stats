package io.repository

import io.db.BookTable
import io.db.suspendTransaction
import io.model.Book
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun addBook(book: Book)
}


object BookRepositorySQLite : BookRepository {

    private fun ResultRow.toBook() = Book(
        isbn = this[BookTable.isbn],
        name = this[BookTable.name],
        author = this[BookTable.author],
        pages = this[BookTable.pages]
    )


    override suspend fun getAllBooks() = suspendTransaction {
        BookTable.selectAll().map { it.toBook() }
    }

    override suspend fun addBook(book: Book) {
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
            if (ex.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true ||
                ex.message?.contains("UNIQUE constraint failed") == true
            ) {
                throw BookAlreadyExistsException("A book with ISBN '${book.isbn}' already exists.")
            }
            throw BookRepositoryException("Database error: ${ex.message}")
        }

    }
}

object BookRepositoryFake : BookRepository {
    override suspend fun getAllBooks(): List<Book> {
        return listOf(
            Book(isbn = "1586487981", name = "A Economia dos Pobres", pages = 311),
            Book(isbn = "9786559790760", name = "A Arte da Estatística", pages = 464)
        )
    }

    override suspend fun addBook(book: Book) {
        TODO("Not yet implemented")
    }
}