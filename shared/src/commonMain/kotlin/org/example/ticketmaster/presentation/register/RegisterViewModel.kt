package org.example.ticketmaster.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.ticketmaster.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterState(
    val currentStep: Int = 1,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)


class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())

    val state: StateFlow<RegisterState> = _state.asStateFlow()


    fun onFirstNameChange(name: String) = _state.update { it.copy(firstName = name) }
    fun onLastNameChange(name: String) = _state.update { it.copy(lastName = name) }
    fun onEmailChange(email: String) = _state.update { it.copy(email = email) }
    fun onPhoneChange(phone: String) = _state.update { it.copy(phone = phone) }
    fun onPasswordChange(password: String) = _state.update { it.copy(password = password) }


    fun onNextStep() {
        val current = _state.value.currentStep
        if (current < 3) {
            _state.update { it.copy(currentStep = current + 1) }
        }
    }

    fun onPreviousStep() {
        val current = _state.value.currentStep
        if (current > 1) {
            _state.update { it.copy(currentStep = current - 1) }
        }
    }

    fun register() {
        val currentState = _state.value
        println("Register button clicked! State: $currentState")

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val result = registerUseCase(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                email = currentState.email,
                phone = currentState.phone,
                password = currentState.password
            )
            
            result.fold(
                onSuccess = {
                    println("Register Successful!")
                    _state.update { it.copy(isLoading = false, error = null, isSuccess = true) }
                },
                onFailure = { error ->
                    println("Register Error: ${error.message}")
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }
    
    fun resetState() {
        _state.value = RegisterState()
    }
}