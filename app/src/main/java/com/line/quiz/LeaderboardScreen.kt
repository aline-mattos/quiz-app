package com.line.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.line.quiz.repositories.LeaderboardRepository
import com.line.quiz.model.LeaderboardEntry
import kotlinx.coroutines.launch

@Composable
fun LeaderboardScreen(navController: NavHostController, leaderboardRepository: LeaderboardRepository) {
    var leaderboardEntries by remember { mutableStateOf(emptyList<LeaderboardEntry>()) }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = "getLeaderboardEntries") {
        scope.launch {
            val entries = leaderboardRepository.getTopLeaderboardEntries()
            leaderboardEntries = entries
        }
    }

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
        leaderboardEntries.forEach { entry ->
            Text(
                text = "${entry.playerName}: ${entry.score} points",
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