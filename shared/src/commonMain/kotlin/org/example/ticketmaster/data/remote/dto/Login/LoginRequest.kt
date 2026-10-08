package org.example.ticketmaster.data.remote.dto.Login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)