package org.example.ticketmaster.presentation.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.ticketmaster.domain.usecase.LoginUseCase
import androidx.lifecycle.ViewModel

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email : StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginStatus = MutableStateFlow("Waiting...")
    val loginStatus: StateFlow<String> = _loginStatus.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }
    suspend fun login() {
        _loginStatus.value = "Loading..."

        val result = loginUseCase(email.value, password.value)

        result.fold(
            onSuccess = { token ->
                _loginStatus.value = "Successful! Token: ${token.accessToken}"
            },
            onFailure = { error ->
                _loginStatus.value = "Error: ${error.message}"
            }
        )
    }
}
