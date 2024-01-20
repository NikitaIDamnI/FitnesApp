package com.example.fitnesapp.domain.models

/*
Класс хранит массив всех упражнений
 */
data class DayModel(
    val dayNumber: Int,
    val exercises: String,
    val isDone: Boolean = UNDEFINED_IS_DONE,
    val completedExercises: Int = UNDEFINED_COMPLETED_EXERCISES
) {

    companion object {
        const val UNDEFINED_ID = 1
        const val UNDEFINED_IS_DONE = false
        const val UNDEFINED_COMPLETED_EXERCISES= -1

    }
}
