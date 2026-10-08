package org.example.ticketmaster.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

import org.example.ticketmaster.data.remote.dto.Login.LoginRequest
import org.example.ticketmaster.data.remote.dto.Login.LoginResponse
import org.example.ticketmaster.data.remote.dto.RegisterRequest
import org.example.ticketmaster.data.remote.dto.RegisterResponse
import org.example.ticketmaster.domain.model.AuthToken
import org.example.ticketmaster.domain.repository.AuthRepository

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthToken> {
        return try {
            val response = httpClient.post("http://10.0.2.2:5278/api/Auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }

            val loginResponse = response.body<LoginResponse>()

            val authToken = AuthToken(
                accessToken = loginResponse.accessToken,
                refreshToken = loginResponse.refreshToken
            )
            Result.success(authToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(firstName: String, lastName: String, email: String, phone: String, password: String): Result<Boolean> {
        return try {
            val response = httpClient.post("http://10.0.2.2:5278/api/Auth/register") {
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
}