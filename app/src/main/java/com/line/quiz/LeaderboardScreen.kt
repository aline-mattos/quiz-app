package com.line.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class LeaderboardEntry(val playerName: String, val points: Int)

@Composable
fun LeaderboardScreen(navController: NavHostController, leaderboard: List<LeaderboardEntry>) {
    val lastTenEntries = remember { leaderboard.takeLast(10) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Leaderboard",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        lastTenEntries.sortedByDescending { it.points }.forEach { entry ->
            Text(
                text = "${entry.playerName}: ${entry.points} points",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("initial") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Começar Novo Quiz")
        }
    }
}
