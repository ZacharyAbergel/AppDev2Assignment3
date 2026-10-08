package com.example.assignment3

/**
 * Represents one game stored in the user's backlog.
 *
 * @property id Unique identifier assigned by [GameProvider].
 * @property title Name of the game.
 * @property platform Platform on which the game is played.
 * @property genre Game genre, such as RPG, action, or strategy.
 * @property hoursPlayed Total number of hours entered by the user.
 * @property imageUrl URL used to load the game's cover image.
 * @property status Current progress status: Backlog, Playing, or Completed.
 */
data class Game(
    val id: Int,
    val title: String,
    val platform: String,
    val genre: String,
    val hoursPlayed: Int,
    val imageUrl: String,
    val status: String
)
