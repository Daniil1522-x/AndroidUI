package com.example.androidui.data.repository

import com.example.androidui.data.local.AppDetailsDao
import com.example.androidui.data.mapper.AppDetailsEntityMapper
import com.example.androidui.data.mapper.AppMapper
import com.example.androidui.data.network.AppApiService
import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val mapper: AppMapper,
    private val entityMapper: AppDetailsEntityMapper,
    private val api: AppApiService,
    private val dao: AppDetailsDao
) : AppRepository {

    override suspend fun getApps(): List<App> {
        return try {
            val catalog = api.getCatalog()
            withContext(Dispatchers.IO) {
                dao.insertAllAppDetails(catalog.map { entityMapper.toEntity(it) })
            }
            catalog.map { mapper.toDomain(it) }
        } catch (e: Exception) {
            val cachedApps = dao.getAllAppDetails().map { entityMapper.toDomain(it) }
            if (cachedApps.isNotEmpty()) {
                cachedApps
            } else {
                throw e
            }
        }
    }

    override suspend fun getAppById(id: String): App {

        val entity = dao.getAppDetails(id).first()

        return if (entity != null) {

            entityMapper.toDomain(entity)

        } else {

            val dto = api.getAppById(id)

            val entityToSave = entityMapper.toEntity(dto)

            withContext(Dispatchers.IO) {
                dao.insertAppDetails(entityToSave)
            }

            mapper.toDomain(dto)
        }
    }
}