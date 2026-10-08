package org.example.ticketmaster

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform