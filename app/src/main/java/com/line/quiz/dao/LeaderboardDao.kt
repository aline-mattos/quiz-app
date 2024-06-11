package com.line.quiz.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.line.quiz.model.LeaderboardEntry

@Dao
interface LeaderboardDao {
    @Query("SELECT * FROM leaderboard ORDER BY score DESC LIMIT 10")
    suspend fun getTopLeaderboardEntries(): List<LeaderboardEntry>

    @Insert
    suspend fun insertLeaderboardEntry(leaderboardEntry: LeaderboardEntry)
}
