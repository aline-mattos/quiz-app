package com.line.quiz.repositories

import android.content.Context
import androidx.room.Room
import com.line.quiz.database.AppDatabase
import com.line.quiz.model.LeaderboardEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeaderboardRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "app-database"
    ).build()

    private val leaderboardDao = db.leaderboardDao()

    suspend fun getTopLeaderboardEntries(): List<LeaderboardEntry> =
        withContext(Dispatchers.IO) {
            leaderboardDao.getTopLeaderboardEntries()
        }

    suspend fun insertLeaderboardEntry(entry: LeaderboardEntry) {
        withContext(Dispatchers.IO) {
            leaderboardDao.insertLeaderboardEntry(entry)
        }
    }
}
