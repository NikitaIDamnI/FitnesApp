package com.example.fitnesapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel

class GetDaysListUseCase(
   private val dayRepository: DayRepository
){
      operator fun invoke(): LiveData<List<DayModel>>{
        return dayRepository.getDaysList()
    }
}