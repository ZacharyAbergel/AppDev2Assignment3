package com.example.assignment3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@Composable
fun GameBacklogScreen() {
    val navigator = LocalNavigator.current
    val gameProvider = LocalGameProvider.current

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var selectedStatus by rememberSaveable {
        mutableStateOf("All")
    }

    var sortOption by rememberSaveable {
        mutableStateOf("Title")
    }

    var expandedGameId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    val displayedGames = gameProvider.games
        .filter { game ->
            game.title.contains(
                searchText,
                ignoreCase = true
            )
        }
        .filter { game ->
            selectedStatus == "All" ||
                    game.status == selectedStatus
        }
        .let { filteredGames ->
            when (sortOption) {
                "Hours" -> filteredGames.sortedByDescending {
                    it.hoursPlayed
                }

                else -> filteredGames.sortedBy {
                    it.title.lowercase()
                }
            }
        }

    val totalHours = gameProvider.games.sumOf {
        it.hoursPlayed
    }

    MainLayout(
        screenTitle = "My Game Backlog",
        currentRoute = AppRoute.GameBacklog
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 850.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text =
                        "${gameProvider.games.size} games • " +
                                "$totalHours total hours",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    label = {
                        Text("Search by title")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Filter by status",
                    style = MaterialTheme.typography.labelLarge
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        ),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "All",
                        "Backlog",
                        "Playing",
                        "Completed"
                    ).forEach { status ->
                        FilterChip(
                            selected =
                                selectedStatus == status,
                            onClick = {
                                selectedStatus = status
                            },
                            label = {
                                Text(status)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sort games",
                    style = MaterialTheme.typography.labelLarge
                )

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = sortOption == "Title",
                        onClick = {
                            sortOption = "Title"
                        },
                        label = {
                            Text("Title")
                        }
                    )

                    FilterChip(
                        selected = sortOption == "Hours",
                        onClick = {
                            sortOption = "Hours"
                        },
                        label = {
                            Text("Hours Played")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (gameProvider.games.isEmpty()) {
                    EmptyBacklogMessage(
                        title = "Your backlog is empty",
                        message =
                            "Add a game to begin building " +
                                    "your collection.",
                        buttonText = "Add a Game",
                        onButtonClick = {
                            navigator.navigate(
                                AppRoute.AddGame
                            )
                        }
                    )
                } else if (displayedGames.isEmpty()) {
                    EmptyBacklogMessage(
                        title = "No matching games",
                        message =
                            "Try changing your search " +
                                    "or selected filter.",
                        buttonText = "Clear Filters",
                        onButtonClick = {
                            searchText = ""
                            selectedStatus = "All"
                        }
                    )
                } else {
                    LazyColumn(
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = displayedGames,
                            key = { game ->
                                game.id
                            }
                        ) { game ->
                            GameBacklogCard(
                                game = game,
                                expanded =
                                    expandedGameId == game.id,
                                onExpand = {
                                    expandedGameId =
                                        if (
                                            expandedGameId ==
                                            game.id
                                        ) {
                                            null
                                        } else {
                                            game.id
                                        }
                                },
                                onViewDetails = {
                                    navigator.navigate(
                                        AppRoute.GameDetails(
                                            title = game.title,
                                            platform =
                                                game.platform,
                                            genre = game.genre,
                                            hoursplayed =
                                                game.hoursPlayed,
                                            imageUrl =
                                                game.imageUrl,
                                            status = game.status
                                        )
                                    )
                                },
                                onRemove = {
                                    gameProvider.removeGame(game)

                                    if (
                                        expandedGameId == game.id
                                    ) {
                                        expandedGameId = null
                                    }
                                }
                            )
                        }

                        item {
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameBacklogCard(
    game: Game,
    expanded: Boolean,
    onExpand: () -> Unit,
    onViewDetails: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onExpand()
            },
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = game.imageUrl,
                    contentDescription =
                        "${game.title} cover image",
                    placeholder = painterResource(
                        Res.drawable.ic_sports_esports
                    ),
                    error = painterResource(
                        Res.drawable.ic_sports_esports
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(
                            width = 90.dp,
                            height = 110.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = game.title,
                        style =
                            MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = game.platform,
                        style =
                            MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text =
                            "${game.hoursPlayed} hours played",
                        style =
                            MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color =
                            MaterialTheme.colorScheme
                                .primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = game.status,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            ),
                            color =
                                MaterialTheme.colorScheme
                                    .onPrimaryContainer
                        )
                    }
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Genre: ${game.genre}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onViewDetails,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("View Details")
                    }

                    OutlinedButton(
                        onClick = onRemove,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Remove")
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap to show options",
                    style = MaterialTheme.typography.labelMedium,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmptyBacklogMessage(
    title: String,
    message: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onButtonClick
        ) {
            Text(buttonText)
        }
    }
}