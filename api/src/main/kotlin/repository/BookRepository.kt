package io.repository

import io.db.BookTable
import io.db.suspendTransaction
import io.model.Book
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

interface BookRepository {
    suspend fun getAllBooks(): List<Book>
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
}

object BookRepositoryFake : BookRepository {
    override suspend fun getAllBooks(): List<Book> {
        return listOf(
            Book(isbn = "1586487981", name = "A Economia dos Pobres", pages = 311),
            Book(isbn = "9786559790760", name = "A Arte da Estatística", pages = 464)
        )
    }
}