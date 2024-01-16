package com.example.fitnesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DaysListItemBinding

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


 class DaysAdapter(private var listener: Listener): ListAdapter<DayModel, DaysAdapter.DayHolder>(MyComparator()) {  // Здесь будет лист
//listener -> это нащ слушатель

     class DayHolder(view: View) : RecyclerView.ViewHolder(view) { //Здесть будет наша разметка
         private val binding = DaysListItemBinding.bind(view) // добавляем View Binding чтобы был быстрый доступ

         fun setData(dayModel: DayModel, listener: Listener) =
             with(binding) {   // Заполняет View (with) нужен чтобы был быстрый доступ к binding. Принимает наш дата класс

                 var name ="${root.context.resources.getString(R.string.day)}" + " ${adapterPosition + 1} "
                 /* "root.context.getString(R.string.day)" -> обращается к нашему ресурсу где стринг
              adapterPosition  -> это длинна адаптера работает как и size
              Эта переменная заполняет какой день*/
                 tvName.text = name
                 val exCounter = root.context.getString(R.string.exercise) + " ${sizeExercises(dayModel.exception)}" // заполняет сколько здесь упраднений
                 tvExCounter.text = exCounter
                 checkBox.isChecked = dayModel.isDone
                 itemView.setOnClickListener { listener.onClick(dayModel.copy(dayNumber = adapterPosition + 1 )) } // назначеди слущатель на каждый итем
             }
         private fun sizeExercises(exception: String): String {  // считает сколько у нас упражнений
             return exception.split(",").filter { it != "0" }.size.toString()
         }
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder { // Надувает наш макет
         val view = LayoutInflater.from(parent.context).inflate(R.layout.days_list_item,parent,false) // Надувает размнтку макета
         return DayHolder(view)
     }

     override fun onBindViewHolder(holder: DayHolder, position: Int) {// Здесть вы задает что нужно делать
         holder.setData(getItem(position), listener) // getItem -> выдает наш список , мы указываем позици
     }


     class MyComparator(): DiffUtil.ItemCallback<DayModel>(){
         /*
         Чтобы у нас не создавалось постоянно с 0 наши дни, мы создаем этот кдасс который сравнивает их
         одинаковы они или нет , чтобы одинаковые не создавались
          */
         override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
             return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
             return oldItem == newItem
         }

     }

     interface Listener{ // запускается в другои классе
         fun onClick(day: DayModel) // в setData он запускается
     }
 }