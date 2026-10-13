package org.example.ticketmaster.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.ticketmaster.domain.model.Event
import org.example.ticketmaster.domain.repository.EventRepository
import org.example.ticketmaster.domain.usecase.GetTicketTypesUseCase

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

class HomeViewModel(
    private val repository: EventRepository,
    private val getTicketTypesUseCase: GetTicketTypesUseCase
) : ViewModel() {
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

                val eventsWithTickets = events.map { event ->
                    if (event.ticketTypes.isEmpty()) {
                        val ticketsResult = getTicketTypesUseCase(event.id)
                        val tickets = ticketsResult.getOrDefault(emptyList())
                        event.copy(ticketTypes = tickets)
                    } else {
                        event
                    }
                }

                _uiState.value = _uiState.value.copy(
                    featuredEvents = eventsWithTickets,
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