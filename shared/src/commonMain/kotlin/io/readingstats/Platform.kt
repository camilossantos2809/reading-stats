package io.readingstats

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform