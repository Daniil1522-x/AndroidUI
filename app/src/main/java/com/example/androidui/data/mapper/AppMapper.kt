package com.example.androidui.data.mapper

import com.example.androidui.data.dto.AppDto
import com.example.androidui.domain.model.App
import javax.inject.Inject

class AppMapper @Inject constructor() {
    fun toDomain(dto: AppDto): App = App(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconRes = dto.iconRes
    )
}