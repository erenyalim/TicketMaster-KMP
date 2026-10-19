package org.example.ticketmaster

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import org.example.ticketmaster.presentation.detail.EventDetailScreen
import org.example.ticketmaster.presentation.login.LoginScreen
import org.example.ticketmaster.presentation.login.LoginViewModel
import org.example.ticketmaster.presentation.main.MainScreen
import org.example.ticketmaster.presentation.notifications.NotificationsScreen
import org.example.ticketmaster.presentation.register.RegisterScreen
import org.example.ticketmaster.presentation.register.RegisterViewModel
import org.koin.compose.getKoin

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }

    MaterialTheme {
        val navController = rememberNavController()
        var selectedEvent by remember { mutableStateOf<org.example.ticketmaster.domain.model.Event?>(null) }

        NavHost(
            navController = navController,
            startDestination = "main"
        ) {
            composable("main") {
                MainScreen(
                    onNavigateToNotifications = {
                        navController.navigate("notifications")
                    },
                    onNavigateToLogin = {
                        navController.navigate("login")
                    },
                    onEventClick = { event ->
                        selectedEvent = event
                        navController.navigate("event_detail")
                    }
                )
            }
            composable("event_detail") {
                val koin = getKoin()
                val eventDetailViewModel = viewModel { koin.get<org.example.ticketmaster.presentation.detail.EventDetailViewModel>() }

                LaunchedEffect(selectedEvent) {
                    eventDetailViewModel.setEvent(selectedEvent)
                }

                val detailUiState by eventDetailViewModel.uiState.collectAsState()

                EventDetailScreen(
                    uiState = detailUiState,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable("notifications") {
                NotificationsScreen(
                    notifications = emptyList(),
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }
            composable("login") {
                val koin = getKoin()
                val loginViewModel = viewModel { koin.get<LoginViewModel>() }
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("register")
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("register") {
                val koin = getKoin()
                val registerViewModel = viewModel { koin.get<RegisterViewModel>() }
                RegisterScreen(
                    viewModel = registerViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
