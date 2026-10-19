package org.example.ticketmaster.domain.repository

import kotlinx.coroutines.flow.StateFlow
import org.example.ticketmaster.domain.model.AuthToken
import org.example.ticketmaster.domain.model.User

interface AuthRepository {
    val currentUser: StateFlow<User?>
    val isLoggedIn: StateFlow<Boolean>

    suspend fun login(email: String, password: String): Result<AuthToken>
    suspend fun register(firstName: String, lastName: String, email: String, phone: String, password: String): Result<Boolean>
    suspend fun logout()
    suspend fun fetchUserProfile(): Result<User>
}