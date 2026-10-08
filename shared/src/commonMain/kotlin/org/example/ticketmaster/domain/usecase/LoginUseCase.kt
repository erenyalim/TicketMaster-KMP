package org.example.ticketmaster.domain.usecase

import org.example.ticketmaster.domain.model.AuthToken
import org.example.ticketmaster.domain.repository.AuthRepository

class LoginUseCase (private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<AuthToken> {

            if(email.isBlank() || password.isBlank()) {
                return Result.failure(IllegalArgumentException("Email or password can not be empty"))
        }

            return repository.login(email, password)
    }
}