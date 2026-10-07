package com.example.assignment3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed class AppRoute : NavKey {

    @Serializable
    data object AddGame : AppRoute()

    @Serializable
    data class GameDetails(
        val title: String,
        val platform: String,
        val genre: String,
        val playtime: String,
        val imageUrl: String,
        val status: String
    ) : AppRoute()

    @Serializable
    data object GameBacklog : AppRoute()

    @Serializable
    data object About : AppRoute()
}

val backStackConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(
                AppRoute.AddGame::class,
                AppRoute.AddGame.serializer()
            )

            subclass(
                AppRoute.GameDetails::class,
                AppRoute.GameDetails.serializer()
            )

            subclass(
                AppRoute.GameBacklog::class,
                AppRoute.GameBacklog.serializer()
            )

            subclass(
                AppRoute.About::class,
                AppRoute.About.serializer()
            )
        }
    }
}

val LocalNavigator = compositionLocalOf<Navigator> {
    error("No Navigator found! Wrap your UI with CompositionLocalProvider.")
}

@Composable
fun Router() {
    val backStack = rememberNavBackStack(
        backStackConfig,
        AppRoute.AddGame
    )

    val navigator = Navigator(backStack)

    CompositionLocalProvider(LocalNavigator provides navigator) {
        NavDisplay(
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryProvider = entryProvider {
                entry<AppRoute.AddGame> {
                    AddGameScreen()
                }

                entry<AppRoute.GameDetails> { route ->
                    GameDetailsScreen(
                        title = route.title,
                        platform = route.platform,
                        genre = route.genre,
                        playtime = route.playtime,
                        imageUrl = route.imageUrl,
                        status = route.status
                    )
                }

                entry<AppRoute.GameBacklog> {
                    GameBacklogScreen()
                }

                entry<AppRoute.About> {
                    AboutScreen()
                }
            }
        )
    }
}