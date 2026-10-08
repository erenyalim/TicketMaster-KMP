package org.example.ticketmaster.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.ticketmaster.domain.model.Event
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.*

@Composable
fun FeaturedEventsSection(
    events: List<Event>,
    onEventClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.home_featured_events),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        events.chunked(2).forEach { rowEvents ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowEvents.forEach { eventItem ->
                    EventGridCard(
                        event = eventItem,
                        modifier = Modifier.weight(1f),
                        onEventClick = onEventClick
                    )
                }
                if (rowEvents.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}