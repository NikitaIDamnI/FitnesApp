package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.presentation.adapters.DaysAdapter
import com.example.fitnesapp.databinding.FragmentDaysBinding
import com.example.fitnesapp.presentation.viewmodels.DaysFragmentViewModel
import com.example.fitnesapp.utils.DialogManager
import com.example.fitnesapp.utils.FragmentManager


class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding // cоздали VB
    private lateinit var ab: ActionBar
    private lateinit var adapter: DaysAdapter
    private val model: DaysFragmentViewModel by lazy {
        ViewModelProvider(this)[DaysFragmentViewModel::class.java]

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false) // Надули вью
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // model.currentDay = 0 // чтобы не проходил больще наших дней, обнолять
        model.loadingFromResourcesInData()
        updateLeftDays()
        initRcView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_days_massage,
                object : DialogManager.Listener {
                    override fun onClick() {
                        // model.pref?.edit()?.clear()?.apply()
                       // adapter.submitList(model.listDay.value)
                    }
                })

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRcView() = with(binding) {
        ab = (activity as AppCompatActivity).supportActionBar!!
        ab.title = getString(R.string.days)

        adapter = DaysAdapter(this@DaysFragment) // указываем этот фрагмент
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
        adapter.submitList(model.listDay.value)
    }


    //урок 9: https://drive.google.com/file/d/1techY8-GilNuYuWC1-G02tEJQxdmbA4R/view?usp=sharing
    /* private fun fillDaysArray(): ArrayList<DayModel> { //заполняет список для дней с занятий
        val tArray = ArrayList<DayModel>() // содаем список
        var daysDoneCounter = 0
        resources.getStringArray(R.array.day_exercise).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
            tArray.add(DayModel(it, 0, model.getExerciseCount() == exCounter)) // заполняем
        }
        binding.progressBar.max = tArray.size
        tArray.forEach {
            if (it.isDone) daysDoneCounter++
        }
        updateLeftDays(tArray.size - daysDoneCounter, tArray.size)
        return tArray
    }//TODO Заполнить масив дней(ViewModel)

    */

    private fun updateLeftDays() {
        val days =  1//TODO
        val dayPassed = model.dayPassed.value ?: 0
    with(binding)
    {// сколько дней осталось и прогресс бар
        val rDays = getString(R.string.left) + "${ days - dayPassed}" + getString(R.string.left_days)
        tvRestDays.text = rDays
        progressBar.progress =  dayPassed

    }
}

    /*
    Здесь у нас функция собирает данные занятий, за день и заполняет их в лист
    Урок 12: https://drive.google.com/file/d/1HMMf-1Ihi4WFs1PfUEVdV0WLJdmJgYTu/view?usp=sharing
     */

   /* private fun fileExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>() // будет хранить все данные занятий за целый день
        day.exception.split(",")
            .forEach {// разбираем массив всех упраженений и будем заполнять в tempList
                val exerciseList =
                    resources.getStringArray(R.array.exercise) // получаем из ресурса все упражнения в массив
                val exercise = exerciseList[it.toInt()] // получает упражнение
                val exerciseArray = exercise.split("|") // разделяет его

                tempList.add(
                    ExerciseModel(
                        exerciseArray[0],
                        exerciseArray[1],
                        exerciseArray[2],
                        false
                    )
                ) // и записывает полученное упражнение

            }
        model.mutableLiveExercise.value = tempList // передаем лист

    }//TODO Парсит в день все упраждения (Заполнить в Базу)


    */


    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()

    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) { // передать день
            //fileExerciseList(day)
         //   model.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExercisesListFragment.newInstance(day.dayNumber),
                activity as AppCompatActivity
            )
        } else { // сбросить день
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_massage,
                object : DialogManager.Listener {
                    override fun onClick() {
                        //model.savePref(day.dayNumber.toString(),0)
                      //  fileExerciseList(day)
                      //  model.currentDay = day.dayNumber
                        model.updateExercise(day,DayModel.UNDEFINED_COMPLETED_EXERCISES)
                        FragmentManager.setFragment(
                            ExercisesListFragment.newInstance(day.dayNumber),
                            activity as AppCompatActivity)

                    }
                })

        }
    } //TODO обработать
}