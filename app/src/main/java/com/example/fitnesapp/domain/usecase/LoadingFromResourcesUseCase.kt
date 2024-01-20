package com.example.fitnesapp.domain.usecase

import com.example.fitnesapp.domain.DayRepository

class LoadingFromResourcesUseCase(
    private val repository: DayRepository
) {
    suspend operator fun invoke(){
        return repository.loadingFromResources()
    }
}