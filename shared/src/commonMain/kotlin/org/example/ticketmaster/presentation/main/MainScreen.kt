package org.example.ticketmaster.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.ticketmaster.presentation.components.NavigationBar
import org.example.ticketmaster.presentation.home.HomeScreen
import org.example.ticketmaster.presentation.home.HomeViewModel
import org.example.ticketmaster.presentation.profile.ProfileScreen
import org.example.ticketmaster.presentation.profile.ProfileViewModel
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    onNavigateToNotifications: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onEventClick: (org.example.ticketmaster.domain.model.Event) -> Unit,
    homeViewModel: HomeViewModel = koinInject(),
    profileViewModel: ProfileViewModel = koinInject()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val darkBackground = Color(0xFF14171D)

    val homeUiState by homeViewModel.uiState.collectAsState()
    val profileUiState by profileViewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                selectedIndex = selectedTab,
                onItemSelected = { newIndex -> selectedTab = newIndex },
                modifier = Modifier.navigationBarsPadding()
            )
        },
        containerColor = darkBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (selectedTab) {
                0 -> {
                    HomeScreen(
                        uiState = homeUiState,
                        onSearchTextChanged = { _ -> },
                        onExploreClicked = {},
                        onNotificationsClicked = onNavigateToNotifications,
                        onEventClick = onEventClick
                    )
                }
                1 -> {
                    TabPlaceholderScreen(title = "Keşfet & Arama")
                }
                2 -> {
                    TabPlaceholderScreen(title = "Biletlerim & Favoriler")
                }
                3 -> {
                    ProfileScreen(
                        uiState = profileUiState,
                        onNavigateToLogin = onNavigateToLogin,
                        onLogoutClick = {
                            profileViewModel.logout {
                                onNavigateToLogin()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TabPlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF14171D)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
