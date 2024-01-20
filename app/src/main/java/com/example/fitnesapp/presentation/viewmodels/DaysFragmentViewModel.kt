package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.data.repository.DayRepositoryImpl
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.usecase.GetDaysListUseCase
import com.example.fitnesapp.domain.usecase.GetDayUseCase
import com.example.fitnesapp.domain.usecase.LoadingFromResourcesUseCase
import com.example.fitnesapp.domain.usecase.UpdateExerciseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaysFragmentViewModel(private val application: Application) : AndroidViewModel(application) {


    private val repositoryImpl = DayRepositoryImpl(application)

    private val getDaysListUseCase = GetDaysListUseCase(repositoryImpl)
    private val getDayUseCase = GetDayUseCase(repositoryImpl)
    private val loadingFromResources = LoadingFromResourcesUseCase(repositoryImpl)
    private val updateExerciseUseCase = UpdateExerciseUseCase(repositoryImpl)

  private var _listDay = getDaysListUseCase()
    val listDay : LiveData<List<DayModel>>
        get() = _listDay
init {
    loadingFromResourcesInData()
}

    private fun loadingFromResourcesInData(){
        /*
        CoroutineScope(Dispatchers.IO).launch{
            repositoryImpl.cleanDB()
        }
         */
        viewModelScope.launch {
        _listDay = getDaysListUseCase()
        if(listDay.value == null) {

                loadingFromResources()
            }
            _listDay= getDaysListUseCase()
        }





    }



    fun updateExercise(dayModel: DayModel,completedExercises: Int){
        viewModelScope.launch {
            updateExerciseUseCase(dayModel, completedExercises)
        }

    }

    private fun dayPassed () : LiveData<Int>{
        val dayPassed = MutableLiveData<Int>()

        return dayPassed

    }




}