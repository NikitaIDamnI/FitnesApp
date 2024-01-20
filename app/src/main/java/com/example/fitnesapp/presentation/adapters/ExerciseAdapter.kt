package com.example.fitnesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.ExerciseListItemBinding
import com.example.fitnesapp.domain.models.ExerciseModel
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class ExerciseAdapter() :
    ListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(MyComparator()) {

    class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding =
            ExerciseListItemBinding.bind(view)

        fun setData(exerciseModel: ExerciseModel) =
            with(binding) {
                tvNameEx.text = exerciseModel.name
                val format = formatCount(exerciseModel.time)
                checkBox2.isChecked = false//exerciseModel.isDone
                tvCount.text = format
                imExercise.setImageDrawable(GifDrawable(root.context.assets, exerciseModel.image))
            }

        private fun formatCount(count: String): String {
            return if (count.startsWith("x")) count
            else TimeUtils.getTime(count.toLong() * 1000)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExerciseHolder,
        position: Int
    ) {
        holder.setData(getItem(position))
    }


    class MyComparator() : DiffUtil.ItemCallback<ExerciseModel>() {

        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

    }
}