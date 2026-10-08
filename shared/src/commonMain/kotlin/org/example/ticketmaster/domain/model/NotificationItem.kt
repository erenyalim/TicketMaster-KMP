package org.example.ticketmaster.domain.model

enum class NotificationType {
    TICKET, REMINDER, PROMO
}

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
    val type: NotificationType
)