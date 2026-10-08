package com.example.assignment3

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf

/**
 * Stores and manages the games added during the current app session.
 *
 * The internal [mutableStateListOf] allows Compose screens to automatically
 * update whenever a game is added or removed. The provider is created by
 * [Router] and shared through [LocalGameProvider].
 *
 * Data is currently stored in memory and is cleared when the application
 * process is closed.
 */
class GameProvider {

    private val gameList = mutableStateListOf<Game>()

    private var nextId = 1

    /**
     * Read-only view of the current game collection.
     */
    val games: List<Game>
        get() = gameList

    /**
     * Creates a game, assigns it a unique ID, and adds it to the collection.
     *
     * @return The newly created [Game].
     */
    fun addGame(
        title: String,
        platform: String,
        genre: String,
        hoursPlayed: Int,
        imageUrl: String,
        status: String
    ): Game {
        val newGame = Game(
            id = nextId,
            title = title,
            platform = platform,
            genre = genre,
            hoursPlayed = hoursPlayed,
            imageUrl = imageUrl,
            status = status
        )

        gameList.add(newGame)
        nextId++

        return newGame
    }

    /**
     * Removes [game] from the collection.
     *
     * If the game is not present, the collection remains unchanged.
     */
    fun removeGame(game: Game) {
        gameList.remove(game)
    }
}

/**
 * Composition local used to make the shared [GameProvider] available
 * throughout the application's composable hierarchy.
 *
 * Accessing it outside the provider configured in [Router] produces an error.
 */
val LocalGameProvider = compositionLocalOf<GameProvider> {
    error(
        "No GameProvider found! " +
                "Wrap the app with CompositionLocalProvider."
    )
}