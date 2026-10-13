package org.example.ticketmaster.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.ticketmaster.presentation.components.NavigationBar
import org.example.ticketmaster.presentation.home.HomeScreen
import org.example.ticketmaster.presentation.home.HomeViewModel
import org.example.ticketmaster.presentation.profile.ProfileScreen
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    onNavigateToNotifications: () -> Unit,
    onEventClick: (org.example.ticketmaster.domain.model.Event) -> Unit,
    viewModel: HomeViewModel = koinInject()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val darkBackground = Color(0xFF14171D)

    val homeUiState by viewModel.uiState.collectAsState()

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
                        onSearchTextChanged = { newText ->

                        },
                        onExploreClicked = {
                            // TODO: Keşfet butonuna tıklandığında yapılacak işlemler (API isteği vs.)
                        },
                        onNotificationsClicked = onNavigateToNotifications,
                        onEventClick = onEventClick
                    )
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {
                    ProfileScreen()
                }
            }
        }
    }
}