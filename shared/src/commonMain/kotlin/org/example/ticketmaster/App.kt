package org.example.ticketmaster

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.ticketmaster.di.appModule
import org.example.ticketmaster.presentation.detail.EventDetailScreen
import org.example.ticketmaster.presentation.login.LoginScreen
import org.example.ticketmaster.presentation.login.LoginViewModel
import org.example.ticketmaster.presentation.main.MainScreen
import org.example.ticketmaster.presentation.register.RegisterScreen
import org.example.ticketmaster.presentation.register.RegisterViewModel
import org.example.ticketmaster.presentation.notifications.NotificationsScreen
import org.koin.compose.getKoin


@Composable
fun App() {
    MaterialTheme {
            val navController = rememberNavController()


            NavHost(
                navController = navController,
                startDestination = "main"
            ) {
                composable("main") {
                    MainScreen(
                        onNavigateToNotifications = {
                            navController.navigate("notifications")
                        },
                        onEventClick = {
                            navController.navigate("event_detail")
                        }
                    )
                }
                composable("event_detail") {
                    EventDetailScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("notifications") {
                    NotificationsScreen(
                        notifications = emptyList(), // TODO: Bu listeyi NotificationViewModel'dan çekilecek
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
                        onNavigateToRegister = {
                            navController.navigate("register")
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