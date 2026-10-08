package com.example.assignment3

/**
 * Describes the platform currently running the shared application.
 */
interface Platform {
    /** Human-readable platform and version information. */
    val name: String
}

/**
 * Returns the platform-specific [Platform] implementation.
 */
expect fun getPlatform(): Platform