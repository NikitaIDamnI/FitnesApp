package com.example.fitnesapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.ExerciseModel

class ResetExerciseUseCase(
    private val repository: DayRepository
) {
    operator fun invoke():LiveData<ExerciseModel>{
       return repository.resetExercise()
    }
}