package com.example.fitnesapp.domain.models

import java.lang.Exception
/*
Класс хранит массив всех упражнений
 */
data class DayModel(
    var exception: String, // массив упражнений ()
    var dayNumber: Int,
    var isDone: Boolean // выполненны или нет
)
