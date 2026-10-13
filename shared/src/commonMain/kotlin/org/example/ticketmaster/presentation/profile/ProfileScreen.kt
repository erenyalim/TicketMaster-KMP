package org.example.ticketmaster.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.Res
import ticketmaster.shared.generated.resources.*

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF14171D))
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1C2129))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF292E37)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Eren Yalım",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "eren@example.com",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { /* TODO: Düzenle */ }
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .offset(x = (-8).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF00E5FF), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(Res.string.profile_edit_profile),
                        color = Color(0xFF00E5FF),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF292E37))
                    .clickable { /* TODO: Change Language logic */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.profile_language_toggle),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileCoreActionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.ConfirmationNumber,
                title = stringResource(Res.string.profile_my_tickets)
            )
            ProfileCoreActionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Star,
                title = stringResource(Res.string.profile_badges)
            )
            ProfileCoreActionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Favorite,
                title = stringResource(Res.string.profile_favorites)
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.profile_sec_account),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1C2129))
                    .padding(8.dp)
            ) {
                ProfileMenuItem(icon = Icons.Default.Email, title = stringResource(Res.string.profile_contact_info))
                ProfileMenuItem(icon = Icons.Default.Lock, title = stringResource(Res.string.profile_change_password))
                ProfileMenuItem(icon = Icons.Default.Security, title = stringResource(Res.string.profile_2fa))
                ProfileMenuItem(icon = Icons.Default.Devices, title = stringResource(Res.string.profile_active_sessions))
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.profile_sec_payment),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1C2129))
                    .padding(8.dp)
            ) {
                ProfileMenuItem(icon = Icons.Default.CreditCard, title = stringResource(Res.string.profile_saved_cards))
                ProfileMenuItem(icon = Icons.Default.Description, title = stringResource(Res.string.profile_billing_info))
                ProfileMenuItem(icon = Icons.Default.History, title = stringResource(Res.string.profile_payment_history))
            }
        }
        
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.profile_sec_preferences),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1C2129))
                    .padding(8.dp)
            ) {
                ProfileMenuItem(icon = Icons.Default.Notifications, title = stringResource(Res.string.profile_notifications))
                ProfileMenuItem(icon = Icons.Default.Palette, title = stringResource(Res.string.profile_theme))
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.profile_sec_support),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1C2129))
                    .padding(8.dp)
            ) {
                ProfileMenuItem(icon = Icons.Default.SupportAgent, title = stringResource(Res.string.profile_help_center))
                ProfileMenuItem(icon = Icons.Default.Report, title = stringResource(Res.string.profile_report_issue))
                ProfileMenuItem(icon = Icons.Default.Policy, title = stringResource(Res.string.profile_privacy_policy))
                ProfileMenuItem(icon = Icons.Default.Gavel, title = stringResource(Res.string.profile_terms))
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.profile_sec_data),
                color = Color(0xFFFF4C4C),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1C2129))
                    .padding(8.dp)
            ) {
                ProfileMenuItem(
                    icon = Icons.Default.Download,
                    title = stringResource(Res.string.profile_download_data),
                    contentColor = Color(0xFFFF4C4C)
                )
                ProfileMenuItem(
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    title = stringResource(Res.string.profile_logout),
                    contentColor = Color(0xFFFF4C4C)
                )
                ProfileMenuItem(
                    icon = Icons.Default.Delete,
                    title = stringResource(Res.string.profile_delete_account),
                    contentColor = Color(0xFFFF4C4C)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ProfileCoreActionCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1C2129))
            .clickable { /* Action */ }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF292E37)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    contentColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF292E37)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (contentColor == Color.White) Color.Gray else contentColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = contentColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}