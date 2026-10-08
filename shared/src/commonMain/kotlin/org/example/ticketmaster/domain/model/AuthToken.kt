package org.example.ticketmaster.domain.model

data class AuthToken (
    val accessToken: String,
    val refreshToken: String? = null
)