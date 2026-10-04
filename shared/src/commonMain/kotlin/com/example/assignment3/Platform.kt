package com.example.assignment3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform