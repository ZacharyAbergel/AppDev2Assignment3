package com.example.assignment3.Layout

import assignment3.shared.generated.resources.Res
import assignment3.shared.generated.resources.ic_arrow_back
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.assignment3.LocalNavigator
import org.jetbrains.compose.resources.painterResource

/**
 * Displays the application's shared top app bar.
 *
 * A back button is shown only when the navigation stack contains a previous
 * destination.
 *
 * @param screenTitle Title displayed in the center of the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBar(screenTitle: String) {
    val navigator = LocalNavigator.current

    CenterAlignedTopAppBar(
        title = {
            Text(screenTitle)
        },
        navigationIcon = {
            if (navigator.hasPrevious()) {
                IconButton(
                    onClick = {
                        navigator.pop()
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            Res.drawable.ic_arrow_back
                        ),
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}