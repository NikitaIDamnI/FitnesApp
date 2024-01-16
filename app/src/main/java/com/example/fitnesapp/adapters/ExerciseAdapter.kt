package com.example.fitnesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DaysListItemBinding
import com.example.fitnesapp.databinding.ExerciseListItemBinding
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable


/*
1. `DaysAdapter` - это класс адаптера для `RecyclerView`, который используется для отображения списка `DayModel` в пользовательском интерфейсе. Он наследует `ListAdapter` и использует `MyComparator` для определения изменений в списке данных и их обновления в `RecyclerView`.

2. `DayHolder` - это внутренний класс адаптера, который представляет элемент пользовательского интерфейса, отображаемый в `RecyclerView`. Он использует View Binding для быстрого доступа к разметке элемента.

   - `setData(dayModel: DayModel)` - метод, который заполняет элемент пользовательского интерфейса данными из объекта `DayModel`. Он устанавливает текст для `tvName` и `tvExCounter` на основе данных из `dayModel`, указывая номер дня и количество упражнений.

   - `sizeExercises(exception: String)` - вспомогательная функция для подсчета количества упражнений на основе строки `exception`.

3. `onCreateViewHolder(parent: ViewGroup, viewType: Int)` - переопределенный метод, который создает новый `DayHolder` и связывает его с разметкой элемента списка `days_list_item`.

4. `onBindViewHolder(holder: DayHolder, position: Int)` - переопределенный метод, который вызывает метод `setData` для `DayHolder`, передавая данные из соответствующего элемента списка.

5. `MyComparator` - это класс, который используется для сравнения элементов `DayModel` в списке и определения, нужно ли обновить соответствующий элемент в `RecyclerView`. Он наследует `DiffUtil.ItemCallback` и переопределяет два метода:

   - `areItemsTheSame(oldItem: DayModel, newItem: DayModel)` - сравнивает элементы по их идентификаторам и определяет, одинаковые они или нет.

   - `areContentsTheSame(oldItem: DayModel, newItem: DayModel)` - сравнивает содержимое элементов и определяет, одинаковы ли они.

Этот адаптер позволяет эффективно отображать список `DayModel` в `RecyclerView`, обновляя только те элементы, которые действительно изменились, и предоставляя пользовательскую разметку для каждого элемента.
урок 6: https://drive.google.com/file/d/1o3mZ0O9TZcIoyyrRTDglqrpU7gU-i4Kc/view?usp=sharing
урок 7: https://drive.google.com/file/d/1goB1omUoSYw_v46DCzs2Ef2EUXPaorkx/view?usp=sharing
урок 8: https://drive.google.com/file/d/1iCItZ6BfFQGG10dCJEELB5CIfQP6QaxW/view?usp=sharing
 */

//урок 11: https://drive.google.com/file/d/1Su71MetmKLlZyMaJ_7LWrBISQeMYz1ky/view?usp=sharing -> Делаем наши дни кликабельными


class ExerciseAdapter() :
    ListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(MyComparator()) {  // Здесь будет лист
//listener -> это нащ слушатель

    class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view) { //Здесть будет наша разметка
        private val binding =
            ExerciseListItemBinding.bind(view) // добавляем View Binding чтобы был быстрый доступ

        fun setData(exerciseModel: ExerciseModel) =
            with(binding) {   // Заполняет View (with) нужен чтобы был быстрый доступ к binding. Принимает наш дата класс
                tvNameEx.text = exerciseModel.name
                val format = formatCount(exerciseModel.time)
                checkBox2.isChecked = exerciseModel.isDone
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
    ): ExerciseHolder { // Надувает наш макет
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_item, parent, false) // Надувает размнтку макета
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExerciseHolder,
        position: Int
    ) {// Здесть вы задает что нужно делать
        holder.setData(getItem(position)) // getItem -> выдает наш список , мы указываем позици
    }


    class MyComparator() : DiffUtil.ItemCallback<ExerciseModel>() {
        /*
        Чтобы у нас не создавалось постоянно с 0 наши дни, мы создаем этот кдасс который сравнивает их
        одинаковы они или нет , чтобы одинаковые не создавались
         */
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

    }
}