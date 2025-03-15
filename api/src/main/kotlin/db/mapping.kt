package io.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

object BookTable : Table("book") {
    val isbn = text("isbn", )
    val name = text("name", )
    val author = text("author", ).nullable()
    val pages = integer("pages").nullable()

    override val primaryKey = PrimaryKey(isbn, name = "book_pk")
}

