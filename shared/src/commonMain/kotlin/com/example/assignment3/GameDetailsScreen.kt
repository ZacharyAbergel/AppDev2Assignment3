package com.example.assignment3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import assignment3.shared.generated.resources.Res
import assignment3.shared.generated.resources.ic_sports_esports
import coil3.compose.AsyncImage
import com.example.assignment3.Layout.MainLayout
import org.jetbrains.compose.resources.painterResource

/**
 * Displays the complete information for a selected game.
 *
 * Coil loads the cover image from [imageUrl]. A local controller drawable is
 * shown while loading or when the remote image cannot be loaded.
 *
 * @param title Name of the game.
 * @param platform Platform on which it is played.
 * @param genre Game genre.
 * @param hoursPlayed Total hours entered by the user.
 * @param imageUrl URL of the game's cover image.
 * @param status Current backlog status.
 */
@Composable
fun GameDetailsScreen(
    title: String,
    platform: String,
    genre: String,
    hoursPlayed: Int,
    imageUrl: String,
    status: String
) {
    val navigator = LocalNavigator.current

    MainLayout(
        screenTitle = "Game Details",
        currentRoute = AppRoute.GameDetails(
            title = title,
            platform = platform,
            genre = genre,
            hoursplayed = hoursPlayed,
            imageUrl = imageUrl,
            status = status
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 700.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "$title cover image",
                    placeholder = painterResource(
                        Res.drawable.ic_sports_esports
                    ),
                    error = painterResource(
                        Res.drawable.ic_sports_esports
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = status,
                        color =
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 6.dp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(16.dp)
                    ) {
                        GameInformationRow(
                            label = "Platform",
                            value = platform
                        )

                        GameInformationRow(
                            label = "Genre",
                            value = genre
                        )

                        GameInformationRow(
                            label = "Hours played",
                            value = "$hoursPlayed hours"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            navigator.navigate(AppRoute.AddGame)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add Another")
                    }

                    Button(
                        onClick = {
                            navigator.navigate(
                                AppRoute.GameBacklog
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("View Backlog")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Displays a labelled value in the game-information card.
 *
 * @param label Description of the value.
 * @param value Game information displayed below the label.
 */
@Composable
private fun GameInformationRow(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}