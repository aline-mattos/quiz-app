package com.line.quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leaderboard")
data class LeaderboardEntry(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val playerName: String,
    val score: Int
)

