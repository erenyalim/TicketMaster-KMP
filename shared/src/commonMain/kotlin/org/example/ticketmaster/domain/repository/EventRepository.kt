package org.example.ticketmaster.domain.repository

import org.example.ticketmaster.domain.model.Event

interface EventRepository {
    suspend fun getFeaturedEvents(): List<Event>
}