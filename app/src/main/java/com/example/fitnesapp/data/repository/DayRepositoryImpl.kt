package com.example.fitnesapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnesapp.data.database.AppDatabase
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel

class DayRepositoryImpl(
    application: Application
) : DayRepository {

    val dayModelDao = AppDatabase.getInstance(application).dayModelDao()
    override suspend fun getDaysList(): LiveData<List<DayModel>> {
        return dayModelDao.getDaysList()
    }

    override suspend fun getDay(day: Int): LiveData<DayModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updateDay(day: Int) {
        TODO("Not yet implemented")
    }

    override fun getExerciseList(): LiveData<List<ExerciseModel>> {
        TODO("Not yet implemented")
    }

    override fun resetExercise(): LiveData<ExerciseModel> {
        TODO("Not yet implemented")
    }

    override fun resetExerciseList(): LiveData<List<ExerciseModel>> {
        TODO("Not yet implemented")
    }
}