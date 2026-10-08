package com.example.assignment3

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import assignment3.shared.generated.resources.Res
import assignment3.shared.generated.resources.compose_multiplatform

/**
 * Root composable for the Game Backlog application.
 *
 * Delegates navigation, shared state, and screen creation to [Router].
 * This composable is used by both the Android and Desktop entry points.
 */
@Composable
fun App() {
    MaterialTheme {
        Router()
    }
}