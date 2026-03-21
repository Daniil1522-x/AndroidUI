package com.example.androidui.data.network

import com.example.androidui.data.dto.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApiService {

    @GET("catalog")
    suspend fun getCatalog(): List<AppDto>

    @GET("catalog/{id}")
    suspend fun getAppById(@Path("id") id: String): AppDto
}