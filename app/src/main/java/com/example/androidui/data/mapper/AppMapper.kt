package com.example.androidui.data.mapper

import com.example.androidui.data.dto.AppDto
import com.example.androidui.domain.model.App

fun AppDto.toDomain(): App = App(
    id = id,
    name = name,
    description = description,
    category = category,
    iconRes = iconRes
)
