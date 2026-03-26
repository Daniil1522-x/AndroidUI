package com.example.androidui.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_details")
data class AppDetailsEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String
)
