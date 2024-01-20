package com.example.fitnesapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.fitnesapp.domain.DayRepository
import com.example.fitnesapp.domain.models.DayModel

class GetDayUseCase(
   private val repository: DayRepository
) {
      operator fun invoke(day:Int):LiveData<DayModel>{
      return  repository.getDay(day)
    }
}