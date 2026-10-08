package org.example.ticketmaster.presentation.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.Res
import ticketmaster.shared.generated.resources.*
import androidx.compose.ui.unit.sp

import org.example.ticketmaster.domain.model.NotificationItem
import org.example.ticketmaster.domain.model.NotificationType

@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    onBackClicked: () -> Unit
) {
    val darkBackground = Color(0xFF14171D)
    val accentColor = Color(0xFF00E5FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1C2129))
                    .clickable { onBackClicked() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = stringResource(Res.string.notifications_title),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Mark all read",
                color = accentColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notifications) { notification ->
                NotificationRow(notification = notification)
            }
        }
    }
}

@Composable
fun NotificationRow(notification: NotificationItem) {
    val cardBackground = Color(0xFF1C2129)
    val accentColor = Color(0xFF00E5FF)

    val iconData = when (notification.type) {
        NotificationType.TICKET -> Pair(Icons.Default.ConfirmationNumber, Color(0xFF4CAF50))
        NotificationType.REMINDER -> Pair(Icons.Default.Schedule, Color(0xFFFF9800))
        NotificationType.PROMO -> Pair(Icons.Default.LocalActivity, Color(0xFFE91E63))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBackground)
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(iconData.second.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconData.first,
                contentDescription = null,
                tint = iconData.second,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (notification.type) {
                        NotificationType.TICKET -> stringResource(Res.string.notifications_ticket_purchase)
                        NotificationType.REMINDER -> stringResource(Res.string.notifications_reminder)
                        NotificationType.PROMO -> stringResource(Res.string.notifications_promo)
                    },
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = if (notification.isRead) FontWeight.Medium else FontWeight.Bold
                )

                if (!notification.isRead) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = notification.message,
                color = Color.Gray,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = notification.time,
                color = Color(0xFF5A6070),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationsScreenPreview() {
    NotificationsScreen(
        notifications = emptyList(),
        onBackClicked = {}
    )
}