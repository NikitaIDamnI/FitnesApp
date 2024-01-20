package com.example.fitnesapp.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesapp.domain.models.ExerciseModel

/*
Здесь будут храниться данные
урок 13: https://drive.google.com/file/d/1HMMf-1Ihi4WFs1PfUEVdV0WLJdmJgYTu/view?usp=sharing

 */

class MainViewModel: ViewModel() {
    val mutableLiveExercise = MutableLiveData<ArrayList<ExerciseModel>>() //объект хранит всебе список
    var pref: SharedPreferences? = null // сохраняет выподнен или нет
    var currentDay = 0 // выступает как key для нашего списка

    fun savePref(key: String, value: Int) {
        pref?.edit()?.putInt(key, value)?.apply() // сохраняет на каком дне остановился
    }

    fun getExerciseCount(): Int {
        return pref?.getInt(currentDay.toString(), 0) ?: 0
    }
}