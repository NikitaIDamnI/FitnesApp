package com.example.fitnesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


/*
Класс хранит массив всех упражнений
 */
@Entity(tableName = "days")
data class DayDbModel(
    @PrimaryKey(autoGenerate = false)
    val dayNumber: Int,
    val exception: String, // массив упражнений
    val isDone: Boolean, // выполненны или нет,
    val completedExercises: Int
)

