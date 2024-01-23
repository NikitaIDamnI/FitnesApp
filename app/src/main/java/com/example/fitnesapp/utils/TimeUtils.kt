package com.example.fitnesapp.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesapp.presentation.fragments.COUNT_DOWN_TIMER
import com.example.fitnesapp.presentation.fragments.ExerciseFragment
import java.text.SimpleDateFormat
import java.util.Calendar

object TimeUtils {

    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("mm:ss")

    fun getTime(time: Long): String{
        val cv = Calendar.getInstance()
        cv.timeInMillis = time
        return formatter.format(cv.time)
    }


}