package org.example.ticketmaster.domain.repository

import org.example.ticketmaster.domain.model.AuthToken

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthToken>
    suspend fun register(firstName: String, lastName: String, email: String, phone: String, password: String): Result<Boolean>
}