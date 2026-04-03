package com.example.androidui.domain.usecase
import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository
import javax.inject.Inject
class GetAppsUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(): List<App> = repository.getApps()
}
