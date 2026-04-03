package com.example.androidui.domain.usecase

import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository
import javax.inject.Inject

class GetAppByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): App = repository.getAppById(id)
}
