# Smart Home Hasil Karya

A Kotlin Multiplatform mobile application for smart home device management, built with Compose Multiplatform targeting Android and iOS platforms.

## Overview

Smart Home Hasil Karya is a cross-platform mobile application that enables users to monitor and control smart home devices. The app features device management, automation, history tracking, and user profile management with a modern, clean UI.

## Features

- **Device Management**: Monitor and control smart home devices (lamps, fans, thermometers, locks, etc.)
- **Real-time Device Control**: Toggle device states and update properties
- **History Tracking**: View device usage history
- **Automation**: Set up automated device actions
- **User Profile**: Manage user settings and preferences
- **Notifications**: Get alerts for device state changes and errors

## Architecture

This project follows Clean Architecture principles with clear separation of concerns:

- **Presentation Layer**: Compose UI with MVVM pattern using ViewModels and State management
- **Domain Layer**: Repository interfaces and business logic
- **Data Layer**: Data sources and API integration

### Tech Stack

- **Kotlin Multiplatform**: Shared code across Android and iOS
- **Compose Multiplatform**: UI framework for declarative UI
- **Ktor Client**: Network requests with OkHttp (Android) and Darwin (iOS)
- **Koin**: Dependency injection
- **DataStore**: Local data persistence
- **Kotlinx Serialization**: JSON serialization/deserialization
- **Navigation Compose**: In-app navigation
- **Material 3**: Modern Material Design components

## Project Structure

```
Smart Home Hasil Karya/
├── composeApp/                  # Shared application code
│   └── src/
│       ├── androidMain/         # Android-specific code
│       ├── commonMain/          # Shared code for all platforms
│       │   ├── kotlin/
│       │   │   └── id/co/hasilkarya/smarthome/
│       │   │       ├── core/             # Core utilities, navigation, theme
│       │   │       ├── home/             # Home feature module
│       │   │       │   ├── data/         # Data sources
│       │   │       │   ├── domain/       # Repository interfaces
│       │   │       │   └── presentation/ # UI and ViewModels
│       │   │       └── ...               # Other feature modules
│       │   └── composeResources/         # Shared resources
│       │       ├── drawable/
│       │       ├── font/
│       │       └── values/
│       └── iosMain/             # iOS-specific code
└── iosApp/                      # iOS application entry point
```

## Prerequisites

- **JDK 11** or higher
- **Android Studio** (latest version recommended)
- **Xcode** (for iOS development, macOS only)
- **Kotlin Multiplatform Mobile plugin** for Android Studio

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd smarthomehasilkarya
   ```

2. Open the project in Android Studio or IntelliJ IDEA with KMP plugin installed.

3. Sync Gradle dependencies.

## Build and Run

### Android Application

To build and run the Android app:

#### Using IDE
- Select the Android run configuration from the run widget in your IDE's toolbar
- Click Run

#### Using Terminal
- On Windows:
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```
- On macOS/Linux:
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```

### iOS Application

To build and run the iOS app:

#### Using IDE
- Select the iOS run configuration from the run widget in your IDE's toolbar
- Click Run

#### Using Xcode
- Open the `iosApp` directory in Xcode
- Select a simulator or device
- Build and run from Xcode

## Configuration

### API Configuration
Update the API base URL and endpoints in the data source configurations.

### Dependencies
Check `composeApp/build.gradle.kts` for all dependencies and versions.

## Development

### Adding New Features
1. Create feature modules under `commonMain/kotlin/id/co/hasilkarya/smarthome/`
2. Follow the existing architecture: data → domain → presentation
3. Register new ViewModels and repositories in the DI modules
4. Add navigation destinations in the navigation package

### Code Style
- Follow Kotlin coding conventions
- Use Compose best practices for UI development
- Maintain clean architecture separation

## Recent Updates

- ✅ Device toggle functionality with error notifications
- ✅ Update device properties via API
- ✅ Enhanced UI layout and visuals
- ✅ Login navigation and loading indicators
- ✅ Home screen with device management
- ✅ State management with ViewModels

## Resources

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform Documentation](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Material 3 Design Guidelines](https://m3.material.io/)

## License

[Add your license information here]

## Contact

Hasil Karya
- Application ID: `id.co.hasilkarya.smarthome`
- Version: 1.0
