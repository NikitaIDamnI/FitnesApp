package com.example.fitnesapp.domain.usecase

import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel

class UpdateExerciseUseCase(
   private val repository: DayRepository
) {
    suspend operator fun invoke(dayModel: DayModel, completedExercises:Int){
        repository.updateExercise(dayModel,completedExercises)

    }
}