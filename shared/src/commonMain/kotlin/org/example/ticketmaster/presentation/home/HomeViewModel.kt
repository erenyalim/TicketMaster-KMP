package org.example.ticketmaster.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.ticketmaster.domain.model.Event
import org.example.ticketmaster.domain.repository.EventRepository

data class HomeUiState(
    val searchText: String = "",
    val selectedCountry: String = "Turkey",
    val selectedCity: String = "Istanbul",
    val selectedDate: String = "This Weekend",
    val selectedCategory: String = "Music",
    val banners: List<String> = emptyList(),
    val featuredEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(private val repository: EventRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val events = repository.getFeaturedEvents()
                _uiState.value = _uiState.value.copy(
                    featuredEvents = events,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}