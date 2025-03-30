package io.model

import kotlinx.serialization.Serializable

@Serializable
data class Goal(
    val id: Int,
    val name: String,
    val pagesTotal: Int,
    val booksCount: Int?,
    val balance: Int?,
    val speedIdeal: Int?,
    val speedAverage: Int?,
    val pagesRead: Int,
    val daysAboveGoal: Int?,
    val daysBelowGoal: Int?
)

@Serializable
data class NewGoal(val name: String)