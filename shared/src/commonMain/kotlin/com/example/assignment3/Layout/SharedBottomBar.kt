package com.example.assignment3.Layout

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.assignment3.AppRoute
import com.example.assignment3.LocalNavigator
import assignment3.shared.generated.resources.Res
import assignment3.shared.generated.resources.ic_add_circle
import assignment3.shared.generated.resources.ic_info
import assignment3.shared.generated.resources.ic_sports_esports
import org.jetbrains.compose.resources.painterResource

@Composable
fun SharedBottomBar(currentRoute: AppRoute) {
    val navigator = LocalNavigator.current

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == AppRoute.AddGame,
            onClick = {
                if (currentRoute != AppRoute.AddGame) {
                    navigator.navigate(AppRoute.AddGame)
                }
            },
            icon = {
                Icon(
                    painter = painterResource(
                        Res.drawable.ic_add_circle
                    ),
                    contentDescription = "Add a game"
                )
            },
            label = {
                Text("Add Game")
            }
        )

        NavigationBarItem(
            selected = currentRoute == AppRoute.GameBacklog,
            onClick = {
                if (currentRoute != AppRoute.GameBacklog) {
                    navigator.navigate(AppRoute.GameBacklog)
                }
            },
            icon = {
                Icon(
                    painter = painterResource(
                        Res.drawable.ic_sports_esports
                    ),
                    contentDescription = "Open game backlog"
                )
            },
            label = {
                Text("Backlog")
            }
        )

        NavigationBarItem(
            selected = currentRoute == AppRoute.About,
            onClick = {
                if (currentRoute != AppRoute.About) {
                    navigator.navigate(AppRoute.About)
                }
            },
            icon = {
                Icon(
                    painter = painterResource(
                        Res.drawable.ic_info
                    ),
                    contentDescription = "Open app information"
                )
            },
            label = {
                Text("About")
            }
        )
    }
}