package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.R
import com.example.fitnesapp.data.repository.DayRepositoryImpl
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.usecase.GetDayUseCase
import com.example.fitnesapp.domain.usecase.GetDaysListUseCase
import com.example.fitnesapp.domain.usecase.LoadingFromResourcesUseCase
import com.example.fitnesapp.domain.usecase.UpdateExerciseUseCase
import kotlinx.coroutines.launch

class DaysFragmentViewModel(private val application: Application) : AndroidViewModel(application) {


    private val repositoryImpl = DayRepositoryImpl(application)

    private val getDaysListUseCase = GetDaysListUseCase(repositoryImpl)
    private val loadingFromResources = LoadingFromResourcesUseCase(repositoryImpl)
    private val updateExerciseUseCase = UpdateExerciseUseCase(repositoryImpl)
    private val getDayUseCase = GetDayUseCase(repositoryImpl)

    private var _statusUpdate = MutableLiveData(STATUS_UPDATE_IDLE)
    val statusUpdate : LiveData<String>
        get() = _statusUpdate

    val newDay= MutableLiveData<DayModel>()

    val listDays = getDaysListUseCase()

    val dayPassed = MutableLiveData<Int>()

    private var _leftDays = MutableLiveData<String>()
    val leftDays: LiveData<String>
        get() = _leftDays


    fun loadingFromResourcesInData(list: List<DayModel>) {
        if (list != null) {
        }
        synchronized(list) {
            list.let {
                if (it !== null) {
                } else {
                    viewModelScope.launch {
                        loadingFromResources()
                    }
                }
            }
        }
    }//TODO Решить вопрос с загрузкой(вроде работает)

    fun resetProgress() {
        viewModelScope.launch {
            loadingFromResources()
        }
    }


    fun updateExercise(dayModel: DayModel, completedExercises: Int) {
        _statusUpdate.value = STATUS_UPDATE_UPDATING
        viewModelScope.launch {
            updateExerciseUseCase(dayModel, completedExercises)

            newDay.value = getDayUseCase(dayModel.dayNumber)
            _statusUpdate.value = STATUS_UPDATE_UPDATED
            updateLeftDays()
            Log.d("DaysFragment","newDay ViewModel ${newDay.value}")
            Log.d("DaysFragment","newNumber ViewModel ${dayModel.dayNumber}")
            Log.d("DaysFragment","newNumber ViewModel ${statusUpdate.value}")
        }
    }

    private fun dayPassed() {
        var dayPass = 0
        listDays.value?.map {
            if (it.isDone) {
                dayPass++
            }
        }
        dayPassed.value = dayPass
    }

    fun updateLeftDays() {
        dayPassed()
        val daysThis = listDays.value?.size ?: 1
        val dayPassedThis = dayPassed.value?.toInt() ?: 1

        _leftDays.value =
            application.getString(R.string.left) + "${daysThis - dayPassedThis}" + application
                .getString(R.string.left_days)

    }

   companion object{
      const val STATUS_UPDATE_IDLE = "IDLE"
       const val STATUS_UPDATE_UPDATED = "UPDATED"
       const val STATUS_UPDATE_UPDATING = "UPDATING"



   }

}

