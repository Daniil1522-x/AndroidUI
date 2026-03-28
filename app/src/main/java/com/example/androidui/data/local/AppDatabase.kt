package com.example.androidui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppDetailsEntity::class],
    version = 2,                // было 1
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "apps.db"
    }
}