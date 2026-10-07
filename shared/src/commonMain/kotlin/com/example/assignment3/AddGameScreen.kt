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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.assignment3.Layout.MainLayout

@Composable
fun AddGameScreen() {
    val navigator = LocalNavigator.current
    val gameProvider = LocalGameProvider.current

    var title by rememberSaveable {
        mutableStateOf("")
    }

    var platform by rememberSaveable {
        mutableStateOf("")
    }

    var genre by rememberSaveable {
        mutableStateOf("")
    }

    var hoursPlayedText by rememberSaveable {
        mutableStateOf("")
    }

    var imageUrl by rememberSaveable {
        mutableStateOf("")
    }

    var status by rememberSaveable {
        mutableStateOf("Backlog")
    }

    val hoursPlayed = hoursPlayedText.toIntOrNull()

    val formIsValid =
        title.isNotBlank() &&
                platform.isNotBlank() &&
                genre.isNotBlank() &&
                hoursPlayed != null &&
                hoursPlayed >= 0 &&
                imageUrl.isNotBlank()

    MainLayout(
        screenTitle = "Add a Game",
        currentRoute = AppRoute.AddGame
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = "Game Information",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Enter a game you want to track.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text("Game title")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = platform,
                    onValueChange = {
                        platform = it
                    },
                    label = {
                        Text("Platform")
                    },
                    placeholder = {
                        Text("PC, PlayStation, Xbox...")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = genre,
                    onValueChange = {
                        genre = it
                    },
                    label = {
                        Text("Genre")
                    },
                    placeholder = {
                        Text("RPG, action, strategy...")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = hoursPlayedText,
                    onValueChange = { newValue ->
                        if (newValue.all { character ->
                                character.isDigit()
                            }
                        ) {
                            hoursPlayedText = newValue
                        }
                    },
                    label = {
                        Text("Hours played")
                    },
                    placeholder = {
                        Text("Example: 25")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    supportingText = {
                        if (
                            hoursPlayedText.isNotEmpty() &&
                            hoursPlayed == null
                        ) {
                            Text("Enter a valid number of hours")
                        } else {
                            Text("Enter numbers only")
                        }
                    },
                    isError =
                        hoursPlayedText.isNotEmpty() &&
                                hoursPlayed == null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = {
                        imageUrl = it
                    },
                    label = {
                        Text("Cover image URL")
                    },
                    placeholder = {
                        Text("https://example.com/image.jpg")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Game status",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = status == "Backlog",
                        onClick = {
                            status = "Backlog"
                        },
                        label = {
                            Text("Backlog")
                        }
                    )

                    FilterChip(
                        selected = status == "Playing",
                        onClick = {
                            status = "Playing"
                        },
                        label = {
                            Text("Playing")
                        }
                    )

                    FilterChip(
                        selected = status == "Completed",
                        onClick = {
                            status = "Completed"
                        },
                        label = {
                            Text("Completed")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val validHours =
                            hoursPlayedText.toIntOrNull()

                        if (validHours != null) {
                            val newGame = gameProvider.addGame(
                                title = title.trim(),
                                platform = platform.trim(),
                                genre = genre.trim(),
                                hoursPlayed = validHours,
                                imageUrl = imageUrl.trim(),
                                status = status
                            )

                            navigator.navigate(
                                AppRoute.GameDetails(
                                    title = newGame.title,
                                    platform = newGame.platform,
                                    genre = newGame.genre,
                                    hoursplayed =
                                        newGame.hoursPlayed,
                                    imageUrl = newGame.imageUrl,
                                    status = newGame.status
                                )
                            )
                        }
                    },
                    enabled = formIsValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Game")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}