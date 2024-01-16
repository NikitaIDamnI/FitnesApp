package com.example.fitnesapp.domain

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel

interface DayRepository {

suspend fun getDaysList(): LiveData<List<DayModel>>
suspend fun getDay(day:Int):LiveData<DayModel>
suspend fun updateDay(day:Int)
fun getExerciseList():LiveData<List<ExerciseModel>>
fun resetExercise():LiveData<ExerciseModel>
fun resetExerciseList():LiveData<List<ExerciseModel>>


}