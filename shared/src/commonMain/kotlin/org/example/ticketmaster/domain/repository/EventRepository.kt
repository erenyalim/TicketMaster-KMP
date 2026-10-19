package org.example.ticketmaster.domain.repository

import org.example.ticketmaster.domain.model.Banner
import org.example.ticketmaster.domain.model.Event

interface EventRepository {
    suspend fun getFeaturedEvents(): List<Event>
    suspend fun getBanners(): List<Banner>
}