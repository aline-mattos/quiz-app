package com.line.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ResultsScreen(navController: NavHostController, playerName: String, correctAnswers: Int, points: Int, leaderboard: MutableList<LeaderboardEntry>) {
    LaunchedEffect(Unit) {
        leaderboard.add(LeaderboardEntry(playerName, points))
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Bom trabalho, $playerName! Você acertou $correctAnswers perguntas! Sua pontuação é: $points",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Button(
                    onClick = { navController.navigate("leaderboard") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Ver Leaderboard")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { navController.navigate("initial") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Começar Novo Quiz")
                }
            }
        }
    }
}