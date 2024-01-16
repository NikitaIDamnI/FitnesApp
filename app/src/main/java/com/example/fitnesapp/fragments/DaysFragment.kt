package com.example.fitnesapp.fragments

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
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.adapters.DayModel
import com.example.fitnesapp.adapters.DaysAdapter
import com.example.fitnesapp.adapters.ExerciseModel
import com.example.fitnesapp.databinding.FragmentDaysBinding
import com.example.fitnesapp.utils.DialogManager
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel


class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding // cоздали VB
    private val model: MainViewModel by activityViewModels() // cоздаем переменную VM для сохранения данных
    private lateinit var ab: ActionBar
    private lateinit var adapter: DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysBinding.inflate(inflater, container, false) // Надули вью
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0 // чтобы не проходил больще наших дней, обнолять
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
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
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
        adapter.submitList(fillDaysArray())
    }


    //урок 9: https://drive.google.com/file/d/1techY8-GilNuYuWC1-G02tEJQxdmbA4R/view?usp=sharing
    private fun fillDaysArray(): ArrayList<DayModel> { //заполняет список для дней с занятий
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
    }

    private fun updateLeftDays(restDays: Int, days: Int) =
        with(binding) {// сколько дней осталось и прогресс бар
            val rDays = getString(R.string.left) + "$restDays" + getString(R.string.left_days)
            tvRestDays.text = rDays
            progressBar.progress = days - restDays

        }

    /*
    Здесь у нас функция собирает данные занятий, за день и заполняет их в лист
    Урок 12: https://drive.google.com/file/d/1HMMf-1Ihi4WFs1PfUEVdV0WLJdmJgYTu/view?usp=sharing
     */

    private fun fileExerciseList(day: DayModel) {
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
                ) // и записывает полученное кпражнение

            }
        model.mutableLiveExercise.value = tempList // передаем лист

    }


    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()

    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {
            fileExerciseList(day)
            model.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExercisesListFragment.newInstance(),
                activity as AppCompatActivity
            )
        } else {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_massage,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(),0)
                        fileExerciseList(day)
                        model.currentDay = day.dayNumber
                        FragmentManager.setFragment(
                            ExercisesListFragment.newInstance(),
                            activity as AppCompatActivity)

                    }
                })

        }
    }
}