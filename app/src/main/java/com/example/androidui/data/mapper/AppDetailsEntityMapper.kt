package com.example.androidui.data.mapper

import com.example.androidui.data.dto.AppDto
import com.example.androidui.data.local.AppDetailsEntity
import com.example.androidui.domain.model.App
import javax.inject.Inject

class AppDetailsEntityMapper @Inject constructor() {

    fun toEntity(dto: AppDto): AppDetailsEntity {
        return AppDetailsEntity(
            id = dto.id,
            name = dto.name,
            category = dto.category,
            iconUrl = dto.iconUrl,
            description = dto.description
        )
    }

    fun toDomain(entity: AppDetailsEntity): App {
        return App(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            category = entity.category,
            iconUrl = entity.iconUrl
        )
    }
}