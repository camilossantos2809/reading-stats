package io.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(val id:Int, val isbn: String, val name: String, val author: String?=null, val pages: Int?, val asin: String?=null)

@Serializable
data class NewBook(val isbn: String, val name: String, val author: String?=null, val pages: Int?)

data class EditBook(val id:Int, val isbn: String?=null, val name: String?=null, val author: String?=null, val pages: Int?=null)

@Serializable
data class ReadingBook(val book:Book,val currentPage:Int)