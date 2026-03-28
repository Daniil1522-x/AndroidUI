package com.example.androidui.domain.repository

import com.example.androidui.domain.model.App

interface AppRepository : AppDetailsRepository {

    suspend fun getApps(): List<App>

    suspend fun getAppById(id: String): App
}