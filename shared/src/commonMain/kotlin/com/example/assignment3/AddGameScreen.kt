package com.example.assignment3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment3.AppRoute
import com.example.assignment3.LocalNavigator
import com.example.assignment3.Layout.MainLayout

@Composable
fun AddGameScreen() {
    val navigator = LocalNavigator.current

    MainLayout(
        screenTitle = "Add a Game",
        currentRoute = AppRoute.AddGame
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Game entry form will go here")

            Button(
                onClick = {
                    navigator.navigate(AppRoute.GameBacklog)
                }
            ) {
                Text("View Backlog")
            }
        }
    }
}