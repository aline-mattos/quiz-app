package com.line.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun InitialScreen(onStartQuiz: (String) -> Unit, onLeaderboardClicked: () -> Unit) {
    val playerNameState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Digite seu nome para começar o quiz",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = playerNameState.value,
            onValueChange = { playerNameState.value = it },
            label = { Text("Nome") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { onStartQuiz(playerNameState.value) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Começar Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLeaderboardClicked,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Ver Leaderboard")
        }
    }
}

