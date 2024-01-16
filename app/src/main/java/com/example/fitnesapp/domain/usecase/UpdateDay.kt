package com.example.fitnesapp.domain.usecase

import com.example.fitnesapp.domain.DayRepository

class UpdateDay(private val repository: DayRepository) {
   suspend operator fun invoke(day:Int){
        repository.updateDay(day)
    }

}