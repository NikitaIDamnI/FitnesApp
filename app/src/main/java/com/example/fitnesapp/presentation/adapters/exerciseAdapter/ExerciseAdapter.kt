package com.example.fitnesapp.presentation.adapters.exerciseAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.ExerciseListItemBinding
import com.example.fitnesapp.domain.models.ExerciseModel
import com.example.fitnesapp.presentation.viewmodels.ExercisesListFragmentViewModel
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class ExerciseAdapter() :
    ListAdapter<ExerciseModel, ExerciseListViewHolder>(MyComparatorExerciseList()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseListViewHolder {
        val view = ExerciseListItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
        return ExerciseListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExerciseListViewHolder,
        position: Int
    ) {
        setData(holder,getItem(position))
    }


    fun setData(holder: ExerciseListViewHolder,exerciseModel: ExerciseModel) =
        with(holder.binding) {
            tvNameEx.text = exerciseModel.name
            checkBox2.isChecked = exerciseModel.isDone
            val time = formatTime(exerciseModel.time)
            tvCount.text = time
            imExercise.setImageDrawable(GifDrawable(root.context.assets, exerciseModel.image))
        }


    private fun formatTime(seconds: String): String {
        if (seconds.startsWith("x")) {
            return seconds
        } else {
            val time = seconds.toLong()

            val minutes = time / 60
            val remainingSeconds = time % 60
            return String.format("%02d:%02d", minutes, remainingSeconds)
        }

    }



}