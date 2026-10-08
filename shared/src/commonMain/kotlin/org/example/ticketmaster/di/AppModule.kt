package org.example.ticketmaster.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.ticketmaster.data.repository.AuthRepositoryImpl
import org.example.ticketmaster.data.repository.EventRepositoryImpl
import org.example.ticketmaster.domain.repository.AuthRepository
import org.example.ticketmaster.domain.repository.EventRepository
import org.example.ticketmaster.domain.usecase.LoginUseCase
import org.example.ticketmaster.domain.usecase.RegisterUseCase
import org.example.ticketmaster.presentation.home.HomeViewModel
import org.example.ticketmaster.presentation.login.LoginViewModel
import org.example.ticketmaster.presentation.register.RegisterViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

var appModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }

    single<EventRepository> { EventRepositoryImpl(get()) }
    factory { HomeViewModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}