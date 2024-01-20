package com.example.fitnesapp.domain.usecase

import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel

class UpdateDayUseCase(private val repository: DayRepository) {
   suspend operator fun invoke(day:DayModel){
        repository.updateDay(day)
    }

}