package com.example.assignment3

data class Game(
    val id: Int,
    val title: String,
    val platform: String,
    val genre: String,
    val hoursPlayed: Int,
    val imageUrl: String,
    val status: String
)
