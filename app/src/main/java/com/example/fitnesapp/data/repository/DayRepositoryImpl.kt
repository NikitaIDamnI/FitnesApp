package com.example.fitnesapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.fitnesapp.R
import com.example.fitnesapp.data.database.AppDatabase
import com.example.fitnesapp.data.mappers.MapperDayModel
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.domain.models.ExerciseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DayRepositoryImpl(
    private val application: Application
) : DayRepository {

    private val mapper = MapperDayModel()
    private val db = AppDatabase.getInstance(application)
    private val dayModelDao = db.DayModelDao()

    override fun getDaysList(): LiveData<List<DayModel>> {
        return dayModelDao.getDaysList().map {
            mapper.mapListDayModelDbToListEntity(it)
        }
    }

    override fun getExerciseList(dayModel: DayModel): List<ExerciseModel>{
        val dayIsDone = dayModel.completedExercises
        val listExerciseModel =
            mutableListOf<ExerciseModel>() // будет хранить все данные занятий за целый день
        dayModel.exercises.split(",")
            .forEachIndexed { index, it ->                // разбираем массив всех упраженений и будем заполнять в tempList
                val exerciseListFromRes =
                    application.resources.getStringArray(R.array.exercise) // получаем из ресурса все упражнения в массив
                val exercise = exerciseListFromRes[it.toInt()] // получает упражнение
                val exerciseArray = exercise.split("|") // разделяет его
                val isDone = index <= dayIsDone
                    listExerciseModel.add(
                        ExerciseModel(
                            exerciseArray[0],
                            exerciseArray[1],
                            exerciseArray[2],
                            isDone = isDone
                        )
                    )
                // и записывает полученное упражнение
            }

        return listExerciseModel
    }




    override suspend fun getDay(dayNum: Int): DayModel {
        val day= dayModelDao.getDay(dayNum)
        return mapper.mapDayModelDbToEntity(day)
    }

    override suspend fun updateDay(dayModel: DayModel) {
        dayModelDao.updateDay(mapper.mapEntityToDayModelDb(dayModel))
    }


    override suspend fun updateExercise(dayModel: DayModel, completedExercises: Int) {
        val exerciseString = dayModel.exercises
        val size = sizeExercises(exerciseString)
        val isDone = completedExercises== size
        updateDay(
            DayModel(
                dayModel.dayNumber,
                dayModel.exercises,
                isDone,
                completedExercises,
            )
        )
    }

    suspend fun cleanDB() {
        db.clearAllTables()
    }

    override suspend fun loadingFromResources() {
        var currentDay = DayModel.UNDEFINED_ID
        application.resources.getStringArray(R.array.day_exercise).forEach {
            updateDay(
                DayModel(
                    currentDay++, exercises = it
                )
            )
        }
    }

    private fun sizeExercises(exception: String): Int {
        val  count = exception.split(",").size
        return count
    }

}