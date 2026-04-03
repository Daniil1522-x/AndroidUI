package com.example.androidui.domain.usecase

import com.example.androidui.domain.repository.AppRepository
import javax.inject.Inject

class ToggleWishlistUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String) = repository.toggleWishlist(id)
}
