package com.example.androidui.data.repository

import com.example.androidui.data.mapper.AppMapper
import com.example.androidui.data.network.RetrofitClient
import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val mapper: AppMapper
) : AppRepository {

    override suspend fun getApps(): List<App> =
        RetrofitClient.apiService.getCatalog().map { mapper.toDomain(it) }

    override suspend fun getAppById(id: String): App =
        mapper.toDomain(RetrofitClient.apiService.getAppById(id))
}