package com.example.fitnesapp.domain.models

data class ExerciseModel(
    val name: String, // название упражненя
    val time: String, // время
    val image: String, // изображение
    val isDone : Boolean = UNDEFINED_IS_DONE // пройден
){
    companion object{
        const val UNDEFINED_IS_DONE = false
    }

}
