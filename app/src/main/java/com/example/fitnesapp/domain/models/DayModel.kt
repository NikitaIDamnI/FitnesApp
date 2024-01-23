package com.example.fitnesapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
Класс хранит массив всех упражнений
 */
@Parcelize
data class DayModel(
    val dayNumber: Int = UNDEFINED_ID,
    val exercises: String,
    val isDone: Boolean = UNDEFINED_IS_DONE,
    val completedExercises: Int = UNDEFINED_COMPLETED_EXERCISES
): Parcelable {

    companion object {
        const val UNDEFINED_ID = 1
        const val UNDEFINED_IS_DONE = false
        const val UNDEFINED_COMPLETED_EXERCISES= -1

    }
}
