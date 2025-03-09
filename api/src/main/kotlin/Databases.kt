package io

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDatabases() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

    }
}
