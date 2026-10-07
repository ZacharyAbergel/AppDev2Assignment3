package com.example.assignment3

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf

class GameProvider {

    private val gameList = mutableStateListOf<Game>()

    private var nextId = 1

    val games: List<Game>
        get() = gameList

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

    fun removeGame(game: Game) {
        gameList.remove(game)
    }
}

val LocalGameProvider = compositionLocalOf<GameProvider> {
    error(
        "No GameProvider found! " +
                "Wrap the app with CompositionLocalProvider."
    )
}