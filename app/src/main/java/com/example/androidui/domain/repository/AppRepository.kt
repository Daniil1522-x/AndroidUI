package com.example.androidui.domain.repository

import com.example.androidui.domain.model.App

interface AppRepository {
    fun getApps(): List<App>
}
