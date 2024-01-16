package com.example.fitnesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesapp.domain.models.ExerciseModel


/*
Класс хранит массив всех упражнений
 */
@Entity(tableName = "days")
data class DayModelDb(
    @PrimaryKey(autoGenerate = true)
    var dayNumber: Int,
    var exception: List<ExerciseModel>, // массив упражнений
    var isDone: Boolean // выполненны или нет
)
