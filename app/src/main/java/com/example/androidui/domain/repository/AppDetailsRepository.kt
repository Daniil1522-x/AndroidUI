package com.example.androidui.domain.repository

import com.example.androidui.domain.model.App
import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {

    fun observeAppDetails(id: String): Flow<App>

    suspend fun toggleWishlist(id: String)
}
