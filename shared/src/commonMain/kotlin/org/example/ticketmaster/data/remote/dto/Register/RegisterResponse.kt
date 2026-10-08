package org.example.ticketmaster.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String? = null,
    val success: Boolean = true,
    val errors: List<String>? = null
)