package com.example.androidui.domain.model

sealed interface AppDetailsState {

    data object Loading : AppDetailsState

    data class Content(
        val app: App,
        val descriptionCollapsed: Boolean = false
    ) : AppDetailsState

    data class Error(val message: String? = null) : AppDetailsState
}
