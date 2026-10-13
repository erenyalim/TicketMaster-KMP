package org.example.ticketmaster.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
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
import ticketmaster.shared.generated.resources.*

@Composable
fun EventDetailScreen(
    uiState: EventDetailUiState = EventDetailUiState(),
    onBackClick: () -> Unit
) {
    val event = uiState.event
    val dynamicTicketTypes = uiState.ticketTypes.ifEmpty { event?.ticketTypes ?: emptyList() }
    val darkBackground = Color(0xFF14171D)
    val surfaceColor = Color(0xFF1C2129)
    val cyanAccent = Color(0xFF00E5FF)
    val textGray = Color(0xFFA0A4AB)

    val minPrice = dynamicTicketTypes.minOfOrNull { it.price } ?: event?.startingPrice ?: 0.0
    val startingPriceText = if (minPrice > 0) {
        if (minPrice % 1.0 == 0.0) "$${minPrice.toInt()}" else "$$minPrice"
    } else {
        ""
    }

    Scaffold(
        containerColor = darkBackground,
        bottomBar = {
            if (startingPriceText.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(surfaceColor)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .navigationBarsPadding()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(stringResource(Res.string.event_total_price), color = textGray, fontSize = 12.sp)
                            Text(startingPriceText, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { /*Bilet Alma*/ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A3140)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(56.dp)
                                .width(160.dp)
                        ) {
                            Text(stringResource(Res.string.event_buy_ticket),
                                color = darkBackground,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(Color(0xFF2A3140))
            ) {
                if (!event?.imageUrl.isNullOrBlank()) {
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
                                    modifier = Modifier.size(36.dp),
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f), darkBackground),
                                startY = 300f
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .align(Alignment.TopStart)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }

                    Column(
                        modifier = Modifier.align(Alignment.TopEnd),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.4f))
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                        }
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.4f))
                        ) {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "Like", tint = Color.White)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(stringResource(Res.string.event_seller_format, "TicketMaster"), color = Color.White, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = event?.title.orEmpty(),
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 36.sp
                    )

                    val formattedDate = listOfNotNull(event?.date, event?.time).filter { it.isNotBlank() }.joinToString(" • ")
                    if (formattedDate.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CalendarToday, contentDescription = null, tint = cyanAccent, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(formattedDate, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }

                    val locationText = listOfNotNull(event?.address?.city, event?.address?.country).filter { it.isNotBlank() }.joinToString(", ")
                    if (locationText.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = cyanAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(locationText, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                            Text(stringResource(Res.string.event_view_map), color = textGray, fontSize = 12.sp)
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(darkBackground)
                    .padding(horizontal = 24.dp)
            ) {
                if (!event?.description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = event.description,
                        color = textGray,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(stringResource(Res.string.event_friends_attending), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy((-12).dp)
                ) {
                    for (i in 1..4) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(2.dp, darkBackground, CircleShape)
                                .background(Color.Gray)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, darkBackground, CircleShape)
                            .background(Color(0xFF2A3140)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+54", color = textGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(stringResource(Res.string.event_tickets), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                val sortedTickets = dynamicTicketTypes.sortedBy { it.price }

                if (sortedTickets.isNotEmpty()) {
                    sortedTickets.forEach { ticket ->
                        TicketTypeCard(
                            title = ticket.name,
                            price = "$${ticket.price}",
                            accentColor = cyanAccent,
                            surfaceColor = surfaceColor
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                } else if (uiState.isLoadingTickets) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = cyanAccent, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun TicketTypeCard(
    title: String,
    price: String,
    accentColor: Color,
    surfaceColor: Color,
    onAddClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(surfaceColor)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A3140)),
            contentAlignment = Alignment.Center
        ) {
            Text(price, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(accentColor)
                .clickable { onAddClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF14171D))
        }
    }
}