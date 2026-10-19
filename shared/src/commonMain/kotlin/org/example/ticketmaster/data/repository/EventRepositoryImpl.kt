package org.example.ticketmaster.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.ticketmaster.data.remote.ApiConstants
import org.example.ticketmaster.data.remote.dto.EventResponse
import org.example.ticketmaster.data.remote.dto.banner.BannerResponse
import org.example.ticketmaster.data.remote.dto.banner.toDomain
import org.example.ticketmaster.data.remote.dto.toDomain
import org.example.ticketmaster.domain.model.Banner
import org.example.ticketmaster.domain.model.Event
import org.example.ticketmaster.domain.repository.EventRepository

class EventRepositoryImpl(
    private val httpClient: HttpClient
) : EventRepository {

    override suspend fun getFeaturedEvents(): List<Event> {
        return try {
            val response: List<EventResponse> = httpClient
                .get(ApiConstants.Events.GET_EVENTS)
                .body()

            response.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getBanners(): List<Banner> {
        return try {
            val response: List<BannerResponse> = httpClient
                .get(ApiConstants.Events.GET_BANNERS)
                .body()

            response
                .map { it.toDomain() }
                .filter { it.isActive }
                .sortedBy { it.displayOrder }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
