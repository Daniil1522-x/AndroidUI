package com.example.androidui.domain.model

data class App(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
    val isInWishlist: Boolean = false
)