package com.example.androidui

data class AppModel(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val iconRes: Int
)

val appsList = listOf(
    AppModel(1, "СберБанк Онлайн", "Больше чем банк", "Финансы", android.R.drawable.ic_menu_myplaces),
    AppModel(2, "Яндекс.Браузер", "Быстрый и безопасный браузер", "Инструменты", android.R.drawable.ic_menu_search),
    AppModel(3, "Почта Mail.ru", "Почтовый клиент для любых ящиков", "Инструменты", android.R.drawable.ic_dialog_email),
    AppModel(4, "Яндекс Навигатор", "Парковки и заправки — по пути", "Транспорт", android.R.drawable.ic_menu_compass),
    AppModel(5, "Мой МТС", "Мой МТС — центр экосистемы МТС", "Инструменты", android.R.drawable.ic_menu_call),
    AppModel(6, "Яндекс — с Алисой", "Яндекс — поиск всегда под рукой", "Инструменты", android.R.drawable.ic_menu_agenda),
)