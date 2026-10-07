package com.example.assignment3.Layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.assignment3.AppRoute

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