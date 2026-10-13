package org.example.ticketmaster.data.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import org.example.ticketmaster.domain.model.Address
import org.example.ticketmaster.domain.model.Event
import org.example.ticketmaster.domain.model.EventType
import org.example.ticketmaster.domain.model.TicketType

@Serializable
data class AddressResponse(
    val city: String,
    val country: String,
    val fullAddress: String
)

@Serializable
data class TicketTypeResponse(
    val id: String,
    val name: String,
    val price: Double
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class EventResponse(
    val id: String,
    val title: String,
    val type: Int,
    val date: String,
    val time: String,
    val description: String,
    val capacity: Int,
    val address: AddressResponse,
    @JsonNames("ImageUrl", "imageUrl", "image_url", "image", "Image")
    val imageUrl: String? = null,
    val ticketTypes: List<TicketTypeResponse>? = null
)
fun EventResponse.toDomain(): Event {
    return Event(
        id = this.id,
        title = this.title,
        type = EventType.entries.getOrElse(this.type) { EventType.KONSER },
        date = this.date,
        time = this.time,
        description = this.description,
        capacity = this.capacity,
        address = Address(
            city = this.address.city,
            country = this.address.country,
            fullAddress = this.address.fullAddress
        ),
        imageUrl = this.imageUrl?.takeIf { it.isNotBlank() },
        ticketTypes = this.ticketTypes?.map {
            TicketType(
                id = it.id,
                name = it.name,
                price = it.price
            )
        } ?: emptyList()
    )
}



