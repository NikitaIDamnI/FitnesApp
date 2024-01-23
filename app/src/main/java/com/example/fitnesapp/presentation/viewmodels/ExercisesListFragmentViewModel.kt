package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.data.repository.DayRepositoryImpl
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel
import com.example.fitnesapp.domain.usecase.GetDayUseCase
import com.example.fitnesapp.domain.usecase.GetExerciseListUseCase
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class ExercisesListFragmentViewModel(private val application: Application) :
    AndroidViewModel(application) {

    private val repository = DayRepositoryImpl(application)

    private val getExerciseList = GetExerciseListUseCase(repository)

    fun getList(day: DayModel):LiveData<List<ExerciseModel>>{
        return getExerciseList(day)
    }


}