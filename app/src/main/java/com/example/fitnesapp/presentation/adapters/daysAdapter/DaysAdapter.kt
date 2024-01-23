package com.example.fitnesapp.presentation.adapters.daysAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DaysListItemBinding
import com.example.fitnesapp.domain.models.DayModel

class DaysAdapter(private val context: Context) :
    ListAdapter<DayModel, DaysListViewHolder>(MyComparatorDaysList()) { // Здесь будет лист

    var onDayItemClickListener: ((DayModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysListViewHolder {
        val binding = DaysListItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return DaysListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaysListViewHolder, position: Int) {

        val daysItem = getItem(position)

        with(holder.binding) {
            val name = daysItem.dayNumber.toString()
            tvName.text = name
            val exCounter = root.context.getString(R.string.exercise) +
                    " ${sizeExercises(daysItem.exercises)}"
            tvExCounter.text = exCounter
            checkBox.isChecked = daysItem.isDone
            root.setOnClickListener {
                onDayItemClickListener?.invoke(daysItem)
            }
        }
    }


    private fun sizeExercises(exception: String): String {
        return exception.split(",").filter { it != "0" }.size.toString()
    }


}