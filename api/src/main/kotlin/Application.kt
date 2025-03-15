package io

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.plugins.configureDatabases
import io.plugins.configureHTTP
import io.plugins.configureRouting
import io.plugins.configureSerialization
import io.repository.BookRepositorySQLite

val development: Boolean = System.getProperty("io.ktor.development")?.toBoolean() ?: false

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module,  )
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureRouting(BookRepositorySQLite)
}
