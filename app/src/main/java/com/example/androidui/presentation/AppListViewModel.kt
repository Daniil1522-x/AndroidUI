package com.example.androidui.presentation

import androidx.lifecycle.ViewModel
import com.example.androidui.data.repository.AppRepositoryImpl
import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel(
    private val repository: AppRepository = AppRepositoryImpl()
) : ViewModel() {

    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    private val _showSnackbar = MutableStateFlow(false)
    val showSnackbar: StateFlow<Boolean> = _showSnackbar.asStateFlow()

    init {
        _apps.value = repository.getApps()
    }

    fun onLogoClick() {
        _showSnackbar.value = true
    }

    fun onSnackbarShown() {
        _showSnackbar.value = false
    }
}
