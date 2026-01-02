package io.repository

import io.db.GoalTable
import io.model.Goal
import io.model.NewGoal
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

interface GoalRepository {
    suspend fun getAllGoals(): List<Goal>
    suspend fun addGoal(goal: NewGoal)
}

object GoalRepositorySQLite : GoalRepository {
    private fun ResultRow.toGoal() = Goal(
        id = this[GoalTable.id],
        name = this[GoalTable.name],
        pagesTotal = this[GoalTable.pagesTotal],
        booksCount = this[GoalTable.booksCount],
        balance = this[GoalTable.balance],
        speedIdeal = this[GoalTable.speedIdeal],
        speedAverage = this[GoalTable.speedAverage],
        pagesRead = this[GoalTable.pagesRead],
        daysAboveGoal = this[GoalTable.daysAboveGoal],
        daysBelowGoal = this[GoalTable.daysBelowGoal]
    )

    override suspend fun getAllGoals() = suspendTransaction {
        GoalTable.selectAll().orderBy(GoalTable.name to SortOrder.DESC).map { it.toGoal() }
    }

    override suspend fun addGoal(goal: NewGoal) {
        suspendTransaction {
            GoalTable.insert {
                it[name] = goal.name
            }
        }
    }
}