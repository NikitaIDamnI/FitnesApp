package com.example.fitnesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DaysListItemBinding
import com.example.fitnesapp.domain.models.DayModel

class DaysAdapter(private var listener: Listener)
    : ListAdapter<DayModel, DaysAdapter.DayHolder>(MyComparator()) {  // Здесь будет лист

     class DayHolder(view: View) : RecyclerView.ViewHolder(view) {
         private val binding = DaysListItemBinding.bind(view)

         fun setData(dayModel: DayModel, listener: Listener) =
             with(binding) {

               var name = dayModel.dayNumber.toString()

                 tvName.text = name
                 val exCounter = root.context.getString(R.string.exercise) +
                         " ${sizeExercises(dayModel.exercises)}"
                 tvExCounter.text = exCounter
                 checkBox.isChecked = dayModel.isDone
                 itemView.setOnClickListener {
                     listener.onClick(dayModel)
                 }
             }
         private fun sizeExercises(exception: String): String {
             return exception.split(",").filter { it != "0" }.size.toString()
         }


     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.days_list_item,parent,false) // Надувает размнтку макета
         return DayHolder(view)
     }

     override fun onBindViewHolder(holder: DayHolder, position: Int) {
         holder.setData(getItem(position), listener)
     }




    class MyComparator(): DiffUtil.ItemCallback<DayModel>(){

         override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
             return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
             return oldItem == newItem
         }

     }

     interface Listener{
         fun onClick(day: DayModel)
     }
 }