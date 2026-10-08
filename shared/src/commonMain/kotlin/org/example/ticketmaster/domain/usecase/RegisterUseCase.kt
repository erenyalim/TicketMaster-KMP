package org.example.ticketmaster.domain.usecase

import org.example.ticketmaster.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ): Result<Boolean> {

        if (firstName.isBlank() || lastName.isBlank()) {
            return Result.failure(Exception("First name and last name cannot be empty."))
        }

        if (!email.contains("@")) {
            return Result.failure(Exception("Please enter a valid email address."))
        }

        if (password.length < 8) {
            return Result.failure(Exception("Password must be at least 8 characters long."))
        }

        return repository.register(firstName, lastName, email, phone, password)
    }
}