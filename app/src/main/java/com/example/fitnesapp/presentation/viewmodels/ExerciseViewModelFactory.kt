package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesapp.domain.models.DayModel

class ExerciseViewModelFactory(
    private val application: Application,
    private val dayModel: DayModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(application, dayModel) as T
        }
       throw RuntimeException("Unknown view model class $modelClass")
    }

}