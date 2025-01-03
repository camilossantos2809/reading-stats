package io.readingstats.android.domain

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Book(
    val id: String = "",
    val title: String = "",
    val pages: Int = 0,
    val author: String = "",
    val status: String = "to read",
    val readingProgress: List<DocumentReference>? = null
)

data class Goal(val pages: Int = 0, val books: List<DocumentReference>? = null)

data class ReadingProgress(
    val id: String = "",
    val initialPage: Int = 0,
    val date: Timestamp = Timestamp.now(),
    val lastPage: Int = 0,
    val pagesRead: Int = 0
)