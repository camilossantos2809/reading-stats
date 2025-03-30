package io.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

object BookTable : Table("book") {
    val id = integer("id").autoIncrement()
    val isbn = text("isbn")
    val name = text("name")
    val author = text("author").nullable()
    val pages = integer("pages").nullable()

    override val primaryKey = PrimaryKey(id, name = "book_pk")
}

object GoalTable : Table("goal") {
    val id = integer("id").autoIncrement()
    val name = text("name")
    val pagesTotal = integer("pages_total")
    val booksCount = integer("books_count").nullable()
    val balance = integer("balance").nullable()
    val speedIdeal = integer("speed_ideal").nullable()
    val speedAverage = integer("speed_average").nullable()
    val pagesRead = integer("pages_read").default(0)
    val daysAboveGoal = integer("days_above_goal").nullable()
    val daysBelowGoal = integer("days_below_goal").nullable()

    override val primaryKey = PrimaryKey(id, name = "goal_pk")
}

object BookReadingProgressTable : Table("book_reading_progress") {
    val id = integer("id").autoIncrement()
    val bookId = integer("book_id").references(BookTable.id)
    val dateRead = text("date_read")
    val progress = integer("progress")
    val progressPrevious = integer("progress_previous").default(0)
    val pagesRead = integer("pages_read").default(0)

}