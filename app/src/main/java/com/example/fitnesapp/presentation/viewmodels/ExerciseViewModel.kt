package com.example.fitnesapp.presentation.viewmodels

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesapp.data.repository.DayRepositoryImpl
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel
import com.example.fitnesapp.domain.usecase.GetExerciseListUseCase
import com.example.fitnesapp.domain.usecase.UpdateExerciseUseCase
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val application: Application, private val day: DayModel
) : ViewModel() {

    private val repository = DayRepositoryImpl(application)

    private val getExerciseList = GetExerciseListUseCase(repository)
    private val updateExercises = UpdateExerciseUseCase(repository)

    // Нужно для работы
    private val listExercise = getExerciseList(day)
    private var _completedExercises = MutableLiveData<Int>()
    private var timer: CountDownTimer? = null
    private var _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean>
        get() = _finish

    var finishUI = MutableLiveData<Boolean>()

    //Для View
    val sizeExercise = listExercise.size + DayModel.UNDEFINED_COMPLETED_EXERCISES
    private var _thisExercise = MutableLiveData<ExerciseModel>()
    val thisExercise: LiveData<ExerciseModel>
        get() = _thisExercise
    private var _nextExercise = MutableLiveData<ExerciseModel>()
    val nextExercise: LiveData<ExerciseModel>
        get() = _nextExercise

    private var _formatTime = MutableLiveData<String>()
    val formatTime: LiveData<String>
        get() = _formatTime

    private var _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress
    val completedExercises: LiveData<Int>
        get() = _completedExercises


    init {
        updateExercise()

    }


    private fun updateExercise() {
        if (_completedExercises.value == null) {
            _completedExercises.value = day.completedExercises
        }
        if (_completedExercises.value == DayModel.UNDEFINED_COMPLETED_EXERCISES) {
            _completedExercises.value = START_EXERCISE
        }
        completedExercises.value?.let {completedExercises ->
            if (completedExercises <= sizeExercise) {
                val countExercise = _completedExercises.value ?: day.completedExercises
                _thisExercise.value = listExercise[countExercise]
                if (countExercise < sizeExercise) {
                    _nextExercise.value = listExercise[countExercise + 1]
                }else{

                }
            }
        }


        Log.d("ExerciseViewModel", "completedExercises = ${_completedExercises.value}")
        Log.d("ExerciseViewModel", "size = ${sizeExercise}")
        Log.d("ExerciseViewModel", "finish = ${this.finish.value}")
        Log.d("ExerciseViewModel", "finishUI = ${this.finishUI.value}")

        Log.d("ExerciseViewModel", "next")



        _finish.value.let {
            if (it == null || it == true) {
                finish()
                startTimer()
            }
            if (it != null || it == true) {
                startTimer()
            }

        }


    }

    private fun startTimer() {

        // Отменяем предыдущий таймер, если он был запущен
        timer?.cancel()

        val time = _thisExercise.value?.time ?: "null"

        // Создаем новый таймер
        if (!time.startsWith("x")) {
            timer = object : CountDownTimer(time.toLong() * 1000, 10) {
                override fun onTick(restTime: Long) {
                    _formatTime.value = formatTime(restTime.toString())
                    _progress.value = restTime.toInt()

                }

                override fun onFinish() {
                    finish()
                    nextExercise()
                    updateExercise()
                }
            }.start()
        } else {
            _formatTime.value = time
        }


    }

    private fun formatTime(time: String): String {


        if (time.startsWith("x")) {
            return time
        } else {
            val second = time.toInt()
            val minutes = second / 60
            val remainingSeconds = second % 60
            return String.format("%02d:%02d", minutes, remainingSeconds)
        }
    }


    fun nextExercise() {

        timer?.cancel()
        val count = _completedExercises.value
        _completedExercises.value = count?.plus(1)
        updateExercise()
    }


    private fun update() {
        viewModelScope.launch {
            updateExercises(
                day, _completedExercises.value ?: day.completedExercises
            )
        }

    }

    private fun finish() {
        val finish = _completedExercises.value!! <= (sizeExercise)
        _finish.value = finish
        finishUI()
    }

    private fun finishUI() {
        val finish = _completedExercises.value!! < (sizeExercise)
        finishUI.value = finish
    }




    companion object {
        const val START_EXERCISE = 0
    }


}