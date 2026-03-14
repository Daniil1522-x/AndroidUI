package com.example.androidui.data.repository

import com.example.androidui.data.dto.AppDto
import com.example.androidui.data.mapper.toDomain
import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {

    private val appDtos: List<AppDto> = listOf(
        AppDto(1, "СберБанк Онлайн", "Больше чем банк", "Финансы", android.R.drawable.ic_menu_myplaces),
        AppDto(2, "Яндекс.Браузер", "Быстрый и безопасный браузер", "Инструменты", android.R.drawable.ic_menu_search),
        AppDto(3, "Почта Mail.ru", "Почтовый клиент для любых ящиков", "Инструменты", android.R.drawable.ic_dialog_email),
        AppDto(4, "Яндекс Навигатор", "Парковки и заправки — по пути", "Транспорт", android.R.drawable.ic_menu_compass),
        AppDto(5, "Мой МТС", "Мой МТС — центр экосистемы МТС", "Инструменты", android.R.drawable.ic_menu_call),
        AppDto(6, "Яндекс — с Алисой", "Яндекс — поиск всегда под рукой", "Инструменты", android.R.drawable.ic_menu_agenda),
    )

    override fun getApps(): List<App> = appDtos.map { it.toDomain() }
}
