package io.plugins

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.model.*
import io.repository.BookAlreadyExistsException
import io.repository.BookRepository
import io.repository.BookRepositoryException
import io.repository.GoalRepositorySQLite
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String)

fun Application.configureRouting(repository: BookRepository) {
    routing {
        get("/") {
            call.respondText("Reading Stats API is up and running!")
        }
        get("/books") {
            call.respond(
                repository.getAllBooks()
            )
        }
        post("/books") {
            try {
                val book = call.receive<NewBook>()
                repository.addBook(book)
                call.respond(HttpStatusCode.Created)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Invalid JSON: ${ex.message}"))
            } catch (ex: BookAlreadyExistsException) {
                call.respond(HttpStatusCode.Conflict, ErrorResponse(ex.message))
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Unexpected error: ${ex.message}"))
            }
        }
        put("/books/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            try {
                val body = call.receive<BookPutRequestBody>()
                repository.editBook(
                    EditBook(
                        id = id.toInt(),
                        isbn = body.isbn,
                        name = body.name,
                        author = body.author,
                        pages = body.pages,
                    )
                )
                call.respond(HttpStatusCode.NoContent)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Invalid JSON: ${ex.message}"))
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Unexpected error: ${ex.message}"))
            }
        }
        post("/books/{id}/reading-progress") {
            val bookId = call.parameters["id"]
            if (bookId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            try {
                val body = call.receive<NewBookReadingProgressBody>()
                repository.addReadingProgress(
                    NewBookReadingProgress(
                        bookId = bookId.toInt(),
                        dateRead = body.dateRead,
                        lastPageRead = body.lastPageRead
                    )
                )
                call.respond(HttpStatusCode.Created)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Invalid JSON: ${ex.message}"))
            }catch(ex:BookRepositoryException){
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(ex.message))
            }
        }
        get("/books/{id}/reading-progress") {
            val bookId = call.parameters["id"]
            if (bookId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val result = repository.getReadingProgress(bookId.toInt())
            if (result == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(result)
        }
        get("/goals") {
            //TODO: separate routes
            call.respond(GoalRepositorySQLite.getAllGoals())
        }
    }
}
