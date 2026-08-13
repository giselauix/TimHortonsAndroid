package com.example.timhortonsandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Displays a simple coffee-run countdown timer.
 *
 * The timer can be started, paused, and reset.
 */
@Composable
fun CoffeeRunTimer() {

    val initialSeconds = 5 * 60

    var secondsRemaining by remember {
        mutableIntStateOf(initialSeconds)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }

    /**
     * Runs once per second while the timer is active.
     */
    LaunchedEffect(isRunning, secondsRemaining) {

        if (isRunning && secondsRemaining > 0) {

            delay(1000)

            secondsRemaining--

        } else if (secondsRemaining == 0) {

            isRunning = false
        }
    }

    val minutes =
        secondsRemaining / 60

    val seconds =
        secondsRemaining % 60

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Coffee Run Timer",
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "%02d:%02d".format(
                    minutes,
                    seconds
                ),
                style =
                    MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Button(
                    onClick = {
                        isRunning = !isRunning
                    }
                ) {

                    Text(
                        if (isRunning) {
                            "Pause"
                        } else {
                            "Start"
                        }
                    )
                }

                Button(
                    onClick = {
                        isRunning = false
                        secondsRemaining =
                            initialSeconds
                    }
                ) {

                    Text("Reset")
                }
            }
        }
    }
}