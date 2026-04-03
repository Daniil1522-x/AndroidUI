package com.example.androidui.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidui.domain.model.AppDetailsState
import com.example.androidui.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle["appId"]) { "appId is required" }

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    init {
        observeAppDetails()
        viewModelScope.launch {
            try {
                repository.getAppById(appId)
            } catch (e: Exception) {
                _state.value = AppDetailsState.Error(e.message)
            }
        }
    }

    private fun observeAppDetails() {
        viewModelScope.launch {
            repository.observeAppDetails(appId)
                .catch {
                    _state.value = AppDetailsState.Error(it.message)
                }
                .collect { app ->
                    _state.value = AppDetailsState.Content(
                        app = app,
                        descriptionCollapsed = false
                    )
                }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            repository.toggleWishlist(appId)
        }
    }
}
