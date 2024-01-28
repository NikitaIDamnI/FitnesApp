package com.example.fitnesapp.domain

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel

interface DayRepository {

     fun getDaysList(): LiveData<List<DayModel>>
     fun getExerciseList(dayModel: DayModel):List<ExerciseModel>
     fun getDay(day: Int): LiveData<DayModel>
    suspend fun updateDay(dayModel: DayModel)
    suspend fun updateExercise(dayModel: DayModel, completedExercises: Int)
    suspend fun loadingFromResources()


}