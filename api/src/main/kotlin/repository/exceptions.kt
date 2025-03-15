package io.repository

data class BookAlreadyExistsException(override val message: String) : Exception(message)

data class BookRepositoryException(override val message: String) : Exception(message)