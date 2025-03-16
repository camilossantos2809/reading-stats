package io.repository

import io.db.BookTable
import io.db.suspendTransaction
import io.model.Book
import io.model.EditBook
import io.model.NewBook
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun addBook(book: NewBook)
    suspend fun editBook(book: EditBook)
}


object BookRepositorySQLite : BookRepository {

    private fun ResultRow.toBook() = Book(
        id = this[BookTable.id],
        isbn = this[BookTable.isbn],
        name = this[BookTable.name],
        author = this[BookTable.author],
        pages = this[BookTable.pages]
    )


    override suspend fun getAllBooks() = suspendTransaction {
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
}