package io.plugins

import io.development
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import java.io.File
import java.util.*

val localProperties = Properties().apply {
    load(File("local.properties").reader())
}

fun Application.configureDatabases() {
//    Postgres/docker
//    val dbHost = System.getenv("DB_HOST") ?: "localhost"
//    val dbPort = System.getenv("DB_PORT") ?: "5432"
//    val dbName = System.getenv("DB_NAME") ?: "myprojectdb"
//    val dbUser = System.getenv("DB_USER") ?: "admin"
//    val dbPassword = System.getenv("DB_PASSWORD") ?: "secret"
//    val dbUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
//
//    Database.connect(
//        dbUrl, user = dbUser, password = dbPassword
//    )
    val sqlitePath: String = if (development)  localProperties.getProperty("sqlitePath")?:"" else "data/readingstats.db"
    Database.connect("jdbc:sqlite:$sqlitePath", "org.sqlite.JDBC")
}
