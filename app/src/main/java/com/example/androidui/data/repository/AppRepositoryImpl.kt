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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
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
                val existingById = dao.getAllAppDetails().associateBy { it.id }
                val entities = catalog.map { dto ->
                    val base = entityMapper.toEntity(dto)
                    val wish = existingById[dto.id]?.isInWishlist ?: false
                    base.copy(isInWishlist = wish)
                }
                dao.insertAllAppDetails(entities)
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

    override fun observeAppDetails(id: String): Flow<App> {
        return dao.getAppDetails(id)
            .filterNotNull()
            .map { entity -> entityMapper.toDomain(entity) }
    }

    override suspend fun toggleWishlist(id: String) {
        val current = dao.getAppDetails(id).first()
        current?.let {
            dao.updateWishlistStatus(id, !it.isInWishlist)
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

            mapper.toDomain(dto).copy(isInWishlist = entityToSave.isInWishlist)
        }
    }
}