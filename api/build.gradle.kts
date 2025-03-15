import java.util.*

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "io"
version = "0.0.1"

val localProperties = Properties().apply {
    load(file("local.properties").reader())
}

application {
    mainClass = "io.ApplicationKt"

    val isDevelopment: Boolean = localProperties.getProperty("development")?.toBoolean() ?: false
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.cio)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.client.content.negotiation)
}
