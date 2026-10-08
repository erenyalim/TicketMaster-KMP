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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.*

@Composable
fun EventDetailScreen(
    onBackClick: () -> Unit
) {
    val darkBackground = Color(0xFF14171D)
    val surfaceColor = Color(0xFF1C2129)
    val cyanAccent = Color(0xFF00E5FF)
    val textGray = Color(0xFFA0A4AB)

    Scaffold(
        containerColor = darkBackground,
        bottomBar = {
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
                        Text("$120.00", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
                        Text(stringResource(Res.string.event_seller_format, "Steve Halama"), color = Color.White, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Night Pulse\nConcert", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 36.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, tint = cyanAccent, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("August 14 • 9 PM", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = cyanAccent, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Austin, Texas", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                        Text(stringResource(Res.string.event_view_map), color = textGray, fontSize = 12.sp)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(darkBackground)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "The Night Pulse Concert is certainly the biggest concert in all of texas at this time of the year featuring the legends of rock music. Do not miss your shot at this concert.",
                    color = textGray,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )

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

                Text(stringResource(Res.string.event_ticket_types), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                TicketTypeCard("Standard", "$40", "Board communication\nDetails • $40", cyanAccent, surfaceColor, textGray)
                Spacer(modifier = Modifier.height(12.dp))
                TicketTypeCard("VIP", "$120", "Front row access\nDetails • $120", cyanAccent, surfaceColor, textGray)

                Spacer(modifier = Modifier.height(100.dp)) // Sticky bottom bar için boşluk
            }
        }
    }
}

@Composable
fun TicketTypeCard(title: String, price: String, details: String, accentColor: Color, surfaceColor: Color, textGray: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(surfaceColor)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A3140)),
            contentAlignment = Alignment.Center
        ) {
            Text(price, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(details, color = textGray, fontSize = 12.sp, lineHeight = 16.sp)
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(accentColor)
                .clickable { /* Sepete Ekle */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF14171D))
        }
    }
}