package org.example.ticketmaster.data.remote.dto.Login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String? = null
)