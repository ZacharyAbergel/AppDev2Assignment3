package com.example.assignment3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Android entry point for the Game Backlog application.
 *
 * Creates the Compose UI and launches the shared multiplatform [App]
 * composable. Most application logic and UI are located in the shared module.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App()
        }
    }
}

/**
 * Displays the shared application in Android Studio's Compose preview.
 */
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}