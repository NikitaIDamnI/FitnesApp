package com.example.fitnesapp.presentation.adapters.exerciseAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitnesapp.domain.models.ExerciseModel

class MyComparatorExerciseList : DiffUtil.ItemCallback<ExerciseModel>() {

        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }


}