package com.example.androidui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel : ViewModel() {

    private val _apps = MutableStateFlow(provideApps())
    val apps: StateFlow<List<AppModel>> = _apps.asStateFlow()

    private val _showSnackbar = MutableStateFlow(false)
    val showSnackbar: StateFlow<Boolean> = _showSnackbar.asStateFlow()

    fun onLogoClick() {
        _showSnackbar.value = true
    }

    fun onSnackbarShown() {
        _showSnackbar.value = false
    }

    private fun provideApps(): List<AppModel> = listOf(
        AppModel(1, "СберБанк Онлайн", "Больше чем банк", "Финансы", android.R.drawable.ic_menu_myplaces),
        AppModel(2, "Яндекс.Браузер", "Быстрый и безопасный браузер", "Инструменты", android.R.drawable.ic_menu_search),
        AppModel(3, "Почта Mail.ru", "Почтовый клиент для любых ящиков", "Инструменты", android.R.drawable.ic_dialog_email),
        AppModel(4, "Яндекс Навигатор", "Парковки и заправки — по пути", "Транспорт", android.R.drawable.ic_menu_compass),
        AppModel(5, "Мой МТС", "Мой МТС — центр экосистемы МТС", "Инструменты", android.R.drawable.ic_menu_call),
        AppModel(6, "Яндекс — с Алисой", "Яндекс — поиск всегда под рукой", "Инструменты", android.R.drawable.ic_menu_agenda),
    )
}