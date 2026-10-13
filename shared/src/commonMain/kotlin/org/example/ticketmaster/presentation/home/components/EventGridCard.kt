package org.example.ticketmaster.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.Res
import ticketmaster.shared.generated.resources.get_ticket
import ticketmaster.shared.generated.resources.starting_price

@Composable
fun EventGridCard(
    event: org.example.ticketmaster.domain.model.Event,
    modifier: Modifier = Modifier,
    onEventClick: (org.example.ticketmaster.domain.model.Event) -> Unit
) {
    val cyanAccent = Color(0xFF00E5FF)
    val textGray = Color(0xFFA0A4AB)
    val cardBackground = Color(0xFF1E232E)

    val minPrice = event.startingPrice
    val priceFormatted = if (minPrice > 0) {
        if (minPrice % 1.0 == 0.0) minPrice.toInt().toString() else minPrice.toString()
    } else {
        "0"
    }

    Box(
        modifier = modifier
            .height(260.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(cardBackground)
            .clickable { onEventClick(event) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A3140)),
            contentAlignment = Alignment.Center
        ) {
            if (!event.imageUrl.isNullOrBlank()) {
                SubcomposeAsyncImage(
                    model = event.imageUrl,
                    contentDescription = event.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = cyanAccent,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF14171D).copy(alpha = 0.6f),
                            Color(0xFF14171D)
                        ),
                        startY = 150f
                    )
                )
        )

        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = stringResource(Res.string.starting_price, priceFormatted),
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Like",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(event.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date",
                    tint = textGray,
                    modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(event.date, color = textGray, fontSize = 11.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = textGray,
                    modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(event.address.city, color = textGray, fontSize = 11.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onEventClick(event) },
                colors = ButtonDefaults.buttonColors(containerColor = cyanAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(Res.string.get_ticket),
                    color = Color(0xFF14171D),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}