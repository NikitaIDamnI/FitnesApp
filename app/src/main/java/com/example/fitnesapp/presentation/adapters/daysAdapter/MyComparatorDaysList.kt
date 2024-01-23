package com.example.fitnesapp.presentation.adapters.daysAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitnesapp.domain.models.DayModel

class MyComparatorDaysList(): DiffUtil.ItemCallback<DayModel>(){

    override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return oldItem.dayNumber == newItem.dayNumber
    }

    override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return oldItem == newItem
    }

}