package com.example.assignment3

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Provides a small multiplatform navigation API around a Navigation 3
 * [NavBackStack].
 *
 * Screens use this class instead of directly modifying the back stack.
 *
 * @property backStack Stack containing the application's current navigation
 * destinations.
 */
class Navigator(
    private val backStack: NavBackStack<NavKey>
) {
    /**
     * Current destination at the top of the stack, or `null` when empty.
     */
    val current: NavKey?
        get() = backStack.lastOrNull()

    /**
     * Adds [key] to the top of the navigation stack.
     */
    fun navigate(key: NavKey) {
        backStack += key
    }

    /**
     * Returns to the previous destination.
     *
     * The initial destination is never removed.
     */
    fun pop() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    /**
     * Returns `true` when the user can navigate to a previous destination.
     */
    fun hasPrevious(): Boolean = backStack.size > 1

    /**
     * Removes every destination above the most recent occurrence of [key].
     *
     * The stack remains unchanged if [key] is not present.
     */
    fun popUntil(key: NavKey) {
        val index = backStack.indexOfLast { it == key }

        if (index == -1) {
            return
        }

        while (backStack.lastIndex > index) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    /**
     * Replaces the current destination with [key].
     *
     * If the stack is empty, [key] becomes its first destination.
     */
    fun replace(key: NavKey) {
        if (backStack.isNotEmpty()) {
            backStack.removeAt(backStack.lastIndex)
        }

        backStack += key
    }
}
