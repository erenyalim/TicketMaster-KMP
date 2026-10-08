# TicketMaster KMP Clone

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=ios&logoColor=white)

A production-ready ticketing application built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. This project demonstrates a clean architecture approach for sharing business logic, networking, and UI across Android and iOS platforms.

## 🏗 Architecture & Tech Stack

This project strictly follows **Clean Architecture** principles and the **MVVM** pattern to ensure testability, scalability, and separation of concerns.

- **UI:** Compose Multiplatform (Material 3)
- **Dependency Injection:** Koin
- **Networking:** Ktor Client
- **Image Loading:** Coil-Compose3
- **Asynchronous Programming:** Kotlin Coroutines & StateFlow
- **Localization:** Compose Resources (TR/EN dynamic support)
- **Architecture:** Clean Architecture (Domain, Data, Presentation layers)

### Folder Structure
```text
shared/src/commonMain/kotlin/org/example/ticketmaster/
│
├── data/           # Repositories implementations, DTOs, API calls (Ktor)
├── domain/         # UseCases, Models (Event, Ticket), Repository Interfaces
├── di/             # Koin Dependency Injection Modules
└── presentation/   # Compose UI Screens (Home, Profile, Login), ViewModels
```

## ✨ Features

- **Multi-Language Support:** Fully integrated with Compose Resources for English and Turkish (`strings.xml`).
- **Dynamic Event Parsing:** Backend mapping for Events, Ticket Types, and starting prices.
- **Modern UI/UX**


## 🚀 Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/TicketMaster.git
   ```
2. **Open in Android Studio:** Ensure you have the Kotlin Multiplatform plugin installed.
3. **Run Android App:** Select the `androidApp` configuration and hit Run.
4. **Run iOS App (Mac Only):** Open the `iosApp` workspace in Xcode or run the `iosApp` configuration from Android Studio.

## 🔒 Environment & Backend

Currently, the app is configured to connect to a local ASP.NET Core backend.
- **Local Endpoint:** `http://10.0.2.2:5000/api` (Android Emulator to localhost)
- To connect to a live server, update the `HttpClient` base URL in the `data` layer.

