package org.example.ticketmaster.data.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.example.ticketmaster.data.remote.dto.EventResponse
import org.example.ticketmaster.data.remote.dto.toDomain
import org.example.ticketmaster.domain.model.Address
import org.example.ticketmaster.domain.model.Event
import org.example.ticketmaster.domain.model.EventType
import org.example.ticketmaster.domain.model.TicketType
import org.example.ticketmaster.domain.repository.EventRepository

class EventRepositoryImpl(
    private val httpClient: HttpClient
) : EventRepository {

    override suspend fun getFeaturedEvents(): List<Event> {
        return try {
            val response: List<EventResponse> = httpClient
                .get("http://10.0.2.2:5000/api/events")
                .body()

            response.map { it.toDomain() }

        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: Error Handling
            emptyList()
        }
    }
}