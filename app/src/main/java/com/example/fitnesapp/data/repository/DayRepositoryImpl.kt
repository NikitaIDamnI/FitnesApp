package com.example.fitnesapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.fitnesapp.R
import com.example.fitnesapp.data.database.AppDatabase
import com.example.fitnesapp.data.mappers.MapperDayModel
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel

class DayRepositoryImpl(
    private val application: Application
) : DayRepository {

    private val mapper = MapperDayModel()
    private val db = AppDatabase.getInstance(application)
    private val dayModelDao = db.DayModelDao()

    override  fun getDaysList(): LiveData<List<DayModel>> {
        return dayModelDao.getDaysList().map {
            mapper.mapListDayModelDbToListEntity(it)
        }
    }


    override  fun getDay(day: Int): LiveData<DayModel> {
        return dayModelDao.getDay(day).map { mapper.mapDayModelDbToEntity(it) }
    }

    override suspend fun updateDay(dayModel: DayModel) {
        dayModelDao.updateDay(mapper.mapEntityToDayModelDb(dayModel))
    }


    override suspend fun updateExercise(dayModel: DayModel, completedExercises: Int) {
        val exerciseString = dayModel.exercises
        val isDone = dayModel.completedExercises == sizeExercises(exerciseString)
        updateDay(
            DayModel(
                dayModel.dayNumber,
                dayModel.exercises,
                isDone,
                completedExercises,
            )
        )
    }

    suspend fun cleanDB(){
        db.clearAllTables()
    }

    override suspend fun loadingFromResources() {
        var currentDay = DayModel.UNDEFINED_ID
        application.resources.getStringArray(R.array.day_exercise).forEach {
            val exCounter = it.split(",").size
            updateDay(
                DayModel(
                    currentDay++, exercises = it
                )
            )
        }
    }

    private fun sizeExercises(exception: String): Int {
        return exception.split(",").size.toString().length//.filter { it != "0" }
    }


    private fun parseFileExerciseList(listExerciseString: String): LiveData<List<ExerciseModel>> {
        val listExerciseModel =
            mutableListOf<ExerciseModel>() // будет хранить все данные занятий за целый день
        listExerciseString.split(",")
            .forEach {// разбираем массив всех упраженений и будем заполнять в tempList
                val exerciseList =
                    application.resources.getStringArray(R.array.exercise) // получаем из ресурса все упражнения в массив
                val exercise = exerciseList[it.toInt()] // получает упражнение
                val exerciseArray = exercise.split("|") // разделяет его

                listExerciseModel.add(
                    ExerciseModel(
                        exerciseArray[0],
                        exerciseArray[1],
                        exerciseArray[2],
                    )
                ) // и записывает полученное упражнение

            }
        return MutableLiveData(listExerciseModel)
    }


}