package com.example.androidui.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {

    @Query("SELECT * FROM app_details")
    suspend fun getAllAppDetails(): List<AppDetailsEntity>

    @Query("SELECT * FROM app_details WHERE id = :id LIMIT 1")
    fun getAppDetails(id: String): Flow<AppDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(entity: AppDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAppDetails(entities: List<AppDetailsEntity>)
}
