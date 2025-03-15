package io.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(val isbn: String, val name: String, val author: String?=null, val pages: Int)