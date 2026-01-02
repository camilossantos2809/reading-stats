package io.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database


fun Application.configureDatabases() {
//    Postgres/docker
    val dbHost = System.getenv("DB_HOST") ?: "localhost"
    val dbPort = System.getenv("DB_PORT") ?: "5432"
    val dbName = System.getenv("DB_NAME") ?: "reading_stats"
    val dbUser = System.getenv("DB_USER") ?: "admin"
    val dbPassword = System.getenv("DB_PASSWORD") ?: "secret"
    val dbUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

    Database.connect(
        dbUrl, user = dbUser, password = dbPassword
    )
}
