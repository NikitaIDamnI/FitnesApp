package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.R
import com.example.fitnesapp.data.repository.DayRepositoryImpl
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.usecase.GetDaysListUseCase
import com.example.fitnesapp.domain.usecase.GetDayUseCase
import com.example.fitnesapp.domain.usecase.LoadingFromResourcesUseCase
import com.example.fitnesapp.domain.usecase.UpdateExerciseUseCase
import kotlinx.coroutines.launch

class DaysFragmentViewModel(private val application: Application) : AndroidViewModel(application) {


    private val repositoryImpl = DayRepositoryImpl(application)

    private val getDaysListUseCase = GetDaysListUseCase(repositoryImpl)
    private val loadingFromResources = LoadingFromResourcesUseCase(repositoryImpl)
    private val updateExerciseUseCase = UpdateExerciseUseCase(repositoryImpl)

    private var _listDays = getDaysListUseCase()
    val listDays: LiveData<List<DayModel>>
        get() = _listDays

    val dayPassed = MutableLiveData<Int>()

    private var _leftDays = MutableLiveData<String>()
    val leftDays: LiveData<String>
        get() = _leftDays


    init {
        updateLeftDays()
        loadingFromResourcesInData()
    }

    private fun loadingFromResourcesInData() {
        viewModelScope.launch {
            _listDays = getDaysListUseCase()
            if (listDays.value == null) {

                loadingFromResources()
            }
            _listDays = getDaysListUseCase()
        }
    }

    fun resetProgress() {
        loadingFromResourcesInData()
    }


    fun updateExercise(dayModel: DayModel, completedExercises: Int) {
        viewModelScope.launch {
            updateExerciseUseCase(dayModel, completedExercises)
        }

    }

    private fun dayPassed() {
        var dayPass = 0
        _listDays.value?.map {
            if (it.isDone) {
                dayPass++
            }
        }
        dayPassed.value = dayPass
    }

    private fun updateLeftDays() {
        dayPassed()
        val daysThis = _listDays.value?.size ?: 1
        val dayPassedThis = dayPassed.value?.toInt()?: 1

        _leftDays.value =
            application.getString(R.string.left) + "${daysThis - dayPassedThis}" + application
                .getString(R.string.left_days)

    }


}
