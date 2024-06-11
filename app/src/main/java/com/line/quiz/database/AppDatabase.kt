package com.line.quiz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.line.quiz.dao.LeaderboardDao
import com.line.quiz.model.LeaderboardEntry

@Database(entities = [LeaderboardEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leaderboardDao(): LeaderboardDao
}
