package io.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.repository.BookRepository

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
    }
}
