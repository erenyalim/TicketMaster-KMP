package org.example.ticketmaster.domain.model

enum class EventType {
    KONSER, SPOR, TİYATRO, SİNEMA, FESTİVAL
}

data class Address(
    val city: String,
    val country: String,
    val fullAddress: String
)

data class TicketType(
    val id: String,
    val name: String,
    val price: Double
)

data class Event(
    val id: String,
    val title: String,
    val type: EventType,
    val date: String,
    val time: String,
    val description: String,
    val capacity: Int,
    val address: Address,
    val imageUrl: String? = null,
    val ticketTypes: List<TicketType> = emptyList()
) {
    val startingPrice: Double
        get() = ticketTypes.minOfOrNull { it.price } ?: 0.0
}