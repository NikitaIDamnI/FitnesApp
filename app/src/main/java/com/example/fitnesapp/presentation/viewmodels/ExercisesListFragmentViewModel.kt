package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.data.repository.DayRepositoryImpl
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel
import com.example.fitnesapp.domain.usecase.GetExerciseListUseCase
import com.example.fitnesapp.domain.usecase.UpdateExerciseUseCase
import kotlinx.coroutines.launch

class ExercisesListFragmentViewModel(private val application: Application) :
    AndroidViewModel(application) {

    private val repository = DayRepositoryImpl(application)

    private val getExerciseList = GetExerciseListUseCase(repository)




    fun getList(day: DayModel): List<ExerciseModel> {

        return getExerciseList(day)
    }
}