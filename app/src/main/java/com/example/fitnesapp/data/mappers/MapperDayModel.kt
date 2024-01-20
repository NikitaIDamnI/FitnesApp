package com.example.fitnesapp.data.mappers

import com.example.fitnesapp.data.database.DayDbModel
import com.example.fitnesapp.domain.models.DayModel

class MapperDayModel {
    fun mapDayModelDbToEntity(dayDbModel: DayDbModel) = DayModel(
        dayNumber = dayDbModel.dayNumber,
        exercises = dayDbModel.exception,
        isDone = dayDbModel.isDone,
        completedExercises = dayDbModel.completedExercises


    )

    fun mapEntityToDayModelDb(dayModel: DayModel) = DayDbModel(
        dayNumber = dayModel.dayNumber,
        exception = dayModel.exercises,
        isDone = dayModel.isDone,
        completedExercises = dayModel.completedExercises
    )

    fun mapListDayModelDbToListEntity(listDayDbModel: List<DayDbModel>) = listDayDbModel.map {
        mapDayModelDbToEntity(it)
    }

    fun mapListEntityToListDayModelDb(listDayModel: List<DayModel>) = listDayModel.map {
        mapEntityToDayModelDb(it)
    }




}