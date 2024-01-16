package com.example.fitnesapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel

class GetDayListUseCase(
   private val dayRepository: DayRepository
){
    suspend operator fun invoke(): LiveData<List<DayModel>>{
        return dayRepository.getDaysList()
    }
}