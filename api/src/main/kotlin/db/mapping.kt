package io.db

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.datetime


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
    val recordedAt = datetime("recorded_at")
    val currentPage = integer("current_page").default(0)
}

object BookGoalTable : Table("book_goal") {
    val id = integer("id").autoIncrement()
    val bookId = integer("book_id").references(BookTable.id)
    val goalId = integer("goal_id").references(GoalTable.id)
    val status = text("status").default("to read")
    val rating = integer("rating").nullable()
}