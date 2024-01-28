package com.example.fitnesapp.presentation.viewmodels

import android.annotation.SuppressLint
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
import com.example.fitnesapp.utils.TimeUtils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.max

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
            finish()
        }
        if (_completedExercises.value == DayModel.UNDEFINED_COMPLETED_EXERCISES) {
            _completedExercises.value = START_EXERCISE
        }


        _completedExercises.value?.let { completedExercises ->
            if (completedExercises <= sizeExercise) {
                val countExercise = _completedExercises.value ?: day.completedExercises
                _thisExercise.value = listExercise[countExercise]
                if (countExercise < sizeExercise) {
                    _nextExercise.value = listExercise[countExercise + NEXT_EXERCISE]
                } else {
                    _nextExercise.value =
                        listExercise[countExercise].copy(image = FINISH_GIF, time = FINISH_TIME)
                }
            }
        }

        _finish.value?.let {
            if (it) {
                startTimer()
                finish()
            } else {
                update()

            }
        }


        Log.d("ExerciseViewModel", "completedExercises = ${_completedExercises.value}")


    }

    private fun startTimer() {
        timer?.cancel()
        val time = _thisExercise.value?.time ?: "null"

        if (!time.startsWith("x")) {
            val durationMillis = ((time.toLong()) * 1000L) + 50
            timer = object : CountDownTimer(durationMillis, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    _formatTime.value = formatTime(millisUntilFinished)
                    _progress.value = millisUntilFinished.toInt()
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

    private fun formatTime(millisUntilFinished: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
        )

    }


    fun nextExercise() {

        timer?.cancel()
        val count = _completedExercises.value
        _completedExercises.value = count?.plus(1)
        updateExercise()
    }


     fun update() {
        viewModelScope.launch {
            var count = _completedExercises.value ?: day.completedExercises
            if (count >= listExercise.size) {
                count = listExercise.size
            }
            updateExercises(
                day, count
            )

        }
        Log.d("ExerciseViewModel", "update()")


    }


    private fun finish() {
        val finish = _completedExercises.value!! <= (sizeExercise)
        _finish.value = finish
    }




    companion object {
        const val START_EXERCISE = 0
        const val NEXT_EXERCISE = 1
        const val FINISH_GIF = "finish.gif"
        const val FINISH_TIME = "The End"

    }


}