package com.example.fitnesapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel

class GetExerciseListUseCase(
   private val repository: DayRepository
) {
    operator fun invoke(dayModel: DayModel): LiveData<List<ExerciseModel>> {
        return repository.getExerciseList(dayModel)
    }
}