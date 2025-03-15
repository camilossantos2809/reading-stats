package io.repository

import io.model.Book

object BookRepository {
    fun getAllBooks(): List<Book> {
        return listOf(
            Book(isbn = "1586487981", name = "A Economia dos Pobres", pages = 311),
            Book(isbn = "9786559790760", name = "A Arte da Estatística", pages = 464)
        )
    }
}