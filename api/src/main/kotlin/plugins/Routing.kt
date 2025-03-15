package io.plugins

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.model.Book
import io.repository.BookAlreadyExistsException
import io.repository.BookRepository
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
                val book = call.receive<Book>()
                repository.addBook(book)
                call.respond(HttpStatusCode.NoContent)
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
    }
}
