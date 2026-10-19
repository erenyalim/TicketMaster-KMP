package org.example.ticketmaster.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.ticketmaster.data.remote.ApiConstants
import org.example.ticketmaster.data.remote.dto.RegisterRequest
import org.example.ticketmaster.data.remote.dto.login.LoginRequest
import org.example.ticketmaster.data.remote.dto.login.LoginResponse
import org.example.ticketmaster.domain.model.AuthToken
import org.example.ticketmaster.domain.model.User
import org.example.ticketmaster.domain.repository.AuthRepository

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private var currentToken: AuthToken? = null

    override suspend fun login(email: String, password: String): Result<AuthToken> {
        return try {
            val response = httpClient.post(ApiConstants.Auth.LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }

            val loginResponse = response.body<LoginResponse>()

            val authToken = AuthToken(
                accessToken = loginResponse.accessToken,
                refreshToken = loginResponse.refreshToken
            )
            currentToken = authToken

            val initialUser = User(
                email = email,
                firstName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            )
            _currentUser.value = initialUser
            _isLoggedIn.value = true

            fetchUserProfile()

            Result.success(authToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ): Result<Boolean> {
        return try {
            val response = httpClient.post(ApiConstants.Auth.REGISTER) {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(firstName, lastName, email, phone, password))
            }

            if (response.status.value in 200..299) {
                Result.success(true)
            } else {
                Result.failure(Exception("HTTP Error: ${response.status.value}"))
            }
        } catch (e: Exception) {
            println("REGISTER EXCEPTION: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        currentToken = null
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    override suspend fun fetchUserProfile(): Result<User> {
        val token = currentToken?.accessToken ?: return Result.failure(Exception("Not logged in"))
        return try {
            val user = httpClient.get(ApiConstants.Auth.PROFILE) {
                header("Authorization", "Bearer $token")
            }.body<User>()

            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            _currentUser.value?.let { Result.success(it) } ?: Result.failure(e)
        }
    }
}
