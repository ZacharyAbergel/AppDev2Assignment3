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
import androidx.compose.runtime.remember

/**
 * Defines every destination that can be displayed by the application.
 *
 * Routes are serializable so Navigation 3 can save and restore the back stack.
 * [GameDetails] includes the information required to rebuild its screen.
 */
@Serializable
sealed class AppRoute : NavKey {

    @Serializable
    data object AddGame : AppRoute()

    /**
     * Route containing the game information displayed on the details screen.
     *
     * @property title Name of the selected game.
     * @property platform Game platform.
     * @property genre Game genre.
     * @property hoursplayed Number of hours played.
     * @property imageUrl URL of the cover image.
     * @property status Current progress status.
     */
    @Serializable
    data class GameDetails(
        val title: String,
        val platform: String,
        val genre: String,
        val hoursplayed: Int,
        val imageUrl: String,
        val status: String
    ) : AppRoute()

    @Serializable
    data object GameBacklog : AppRoute()

    @Serializable
    data object About : AppRoute()
}

/**
 * Serialization configuration used to save and restore application routes.
 *
 * Each [AppRoute] subtype must be registered in this module.
 */
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

/**
 * Composition local that makes the application's [Navigator] available
 * to every screen and shared layout component.
 */
val LocalNavigator = compositionLocalOf<Navigator> {
    error("No Navigator found! Wrap your UI with CompositionLocalProvider.")
}

/**
 * Creates the application navigation stack and connects routes to screens.
 *
 * The router also creates the shared [GameProvider]. Both the navigator and
 * provider are supplied through composition locals so screens can access them
 * without passing them through every composable parameter.
 */
@Composable
fun Router() {
    val backStack = rememberNavBackStack(
        backStackConfig,
        AppRoute.AddGame
    )

    val navigator = Navigator(backStack)

    val gameProvider = remember {
        GameProvider()
    }

    CompositionLocalProvider(
        LocalNavigator provides navigator,
        LocalGameProvider provides gameProvider
    ) {
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
                        hoursPlayed = route.hoursplayed,
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