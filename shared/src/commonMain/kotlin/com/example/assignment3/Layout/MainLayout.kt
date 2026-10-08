package com.example.assignment3.Layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.assignment3.AppRoute

/**
 * Provides the common visual structure used by every application screen.
 *
 * The layout places [SharedTopBar] above the content and [SharedBottomBar]
 * below it. Scaffold padding is applied to prevent screen content from being
 * covered by either navigation component.
 *
 * @param screenTitle Title displayed in the top app bar.
 * @param currentRoute Route used to highlight the selected bottom-bar item.
 * @param content Screen-specific content displayed between the app bars.
 */
@Composable
fun MainLayout(
    screenTitle: String,
    currentRoute: AppRoute,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            SharedTopBar(screenTitle)
        },
        bottomBar = {
            SharedBottomBar(currentRoute)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content()
        }
    }
}