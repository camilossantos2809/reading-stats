package io.model

import kotlinx.serialization.Serializable

@Serializable
data class BookPutRequestBody(
    val isbn: String? = null,
    val name: String? = null,
    val author: String? = null,
    val pages: Int? = null
)