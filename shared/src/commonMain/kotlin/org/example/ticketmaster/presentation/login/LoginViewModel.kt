package org.example.ticketmaster.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.ticketmaster.domain.usecase.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginStatus = MutableStateFlow("")
    val loginStatus: StateFlow<String> = _loginStatus.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    suspend fun login(onSuccess: () -> Unit = {}) {
        _isLoading.value = true
        _loginStatus.value = "Giriş yapılıyor..."

        val result = loginUseCase(email.value, password.value)

        _isLoading.value = false

        result.fold(
            onSuccess = { _ ->
                _loginStatus.value = "Başarılı"
                onSuccess()
            },
            onFailure = { error ->
                _loginStatus.value = "Hata: ${error.message ?: "Giriş başarısız"}"
            }
        )
    }
}
