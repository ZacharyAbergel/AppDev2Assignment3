package com.example.assignment3

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
/**Multiplatform-friendly navigation helper for Navigation3.
 * This class provides a small, explicit API for manipulating a NavBackStack
 * without leaking platform-specific navigation concepts. */
class Navigator(
    private val backStack: NavBackStack<NavKey>
) {
    /** The current (top) destination, or null if the stack is empty.    */
    val current: NavKey?
        get() = backStack.lastOrNull()

    /** Push a new screen onto the back stack.  */
    fun navigate(key: NavKey) {
        backStack += key
    }

    /** Remove the current screen and go back.
     * Does nothing if the stack has 0 or 1 entries.     */
    fun pop() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)        }
    }
    /** Returns true if a previous destination exists. */
    fun hasPrevious(): Boolean = backStack.size > 1

    /** Pop back to a specific screen.
     * If the key is found, all entries above it are removed.
     * If the key is not found, the stack is left unchanged.     */
    fun popUntil(key: NavKey) {
        val index = backStack.indexOfLast { it == key }
        if (index == -1) return

        while (backStack.lastIndex > index) {
            backStack.removeAt(backStack.lastIndex)
        }
    }
    /**Replace the current top screen with a new one.
     * If the stack is empty, this behaves like navigate().     */
    fun replace(key: NavKey) {
        if (backStack.isNotEmpty()) {
            backStack.removeAt(backStack.lastIndex)        }
        backStack += key
    }
}
