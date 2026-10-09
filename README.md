# Game Backlog

Game Backlog is a Kotlin and Compose Multiplatform application that allows
users to organize their game collection, record hours played, and track each
game's progress.

The application runs on Android and Desktop using a shared Compose UI.

## Features

- Add games to a shared backlog
- Record title, platform, genre, hours played, cover URL, and status
- Restrict hours played to whole numbers
- View game details and remote cover images
- Search games by title
- Filter games by status
- Sort games alphabetically or by hours played
- Expand backlog cards to display additional actions
- Remove games from the backlog
- Navigate using shared top and bottom app bars
- Run the same shared interface on Android and Desktop

## Technologies

- Kotlin
- Kotlin Multiplatform
- Compose Multiplatform
- Material 3
- Navigation 3
- Kotlin Serialization
- Coil 3

## Project Structure

- `androidApp` — Android entry point and Android resources
- `desktopApp` — Desktop JVM entry point
- `shared/commonMain` — shared screens, models, navigation, state, and layout
- `shared/androidMain` — Android-specific implementation
- `shared/jvmMain` — Desktop-specific implementation
- `shared/commonMain/composeResources` — shared drawable resources

## Main Components

- `Router` configures Navigation 3 and connects routes to screens.
- `Navigator` provides shared navigation operations.
- `GameProvider` stores observable game state.
- `MainLayout` provides the shared top and bottom navigation bars.
- `AddGameScreen` validates and adds games.
- `GameBacklogScreen` displays, filters, sorts, and removes games.
- `GameDetailsScreen` displays complete information about a game.
- `AboutScreen` describes the application and developer.

## Running the Android App

1. Open the project in Android Studio.
2. Allow Gradle synchronization to complete.
3. Start an Android emulator.
4. Select the `androidApp` run configuration.
5. Click Run.

The Android application can also be built with:

```bash
./gradlew :androidApp:assembleDebug

```

On Windows:

```powershell
gradlew.bat :androidApp:assembleDebug
```

## Running the Desktop App

Run the Desktop configuration from Android Studio or use:

```bash
./gradlew :desktopApp:run
```

On Windows:

```powershell
gradlew.bat :desktopApp:run
```

## Current Limitation

Games are stored in memory. They remain available while the application is
running but are cleared when the application process closes.

## Developer

Zachary  
Computer Science Technology  
John Abbott College