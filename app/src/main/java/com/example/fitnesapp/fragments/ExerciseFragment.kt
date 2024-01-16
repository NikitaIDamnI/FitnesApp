package com.example.fitnesapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitnesapp.R
import com.example.fitnesapp.adapters.ExerciseModel
import com.example.fitnesapp.databinding.ExerciseBinding
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class ExerciseFragment : Fragment() {

    private lateinit var binding: ExerciseBinding// cоздали VB
    private var exerciseCounter = 0
    private var timer: CountDownTimer? = null
    private var exList: ArrayList<ExerciseModel>? = null
    private var currentDay = 0
    private val model: MainViewModel by activityViewModels() // cоздаем переменную VM для сохранения данных
    private lateinit var ab: ActionBar // инициализируем Экшен бар

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseBinding.inflate(inflater, container, false) // Надули вью
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay  = model.currentDay
        exerciseCounter = model.getExerciseCount()
        ab = (activity as AppCompatActivity).supportActionBar!!
        model.mutableLiveExercise.observe(viewLifecycleOwner) {//здесь мы получаем данные массива на который нажили
            exList = it
            nextExercise()
        }
        binding.bNext.setOnClickListener {
            timer?.cancel()
            nextExercise()
        }
    }


    private fun nextExercise() {
        if (exerciseCounter < exList?.size!!) {
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExerciseType(ex)
            showNextExercise()

        } else {
            exerciseCounter++
            FragmentManager.setFragment(
                DayFinishFragment.newInstance(),
                activity as AppCompatActivity
            )  // конец упражнений пере

        }

    }

    private fun showExercise(exercise: ExerciseModel?) = with(binding) {
        imMainEx.setImageDrawable(exercise?.image?.let { GifDrawable(root.context.assets, it) })
        tvNameEx.text = exercise?.name
        val title = "$exerciseCounter / ${exList?.size}"
        ab.title = title
    } //показываеи упражнение


    private fun showNextExercise() = with(binding) {
        if (exerciseCounter < exList?.size!!) {
            val ex = exList?.get(exerciseCounter) ?: return
            imNextExercise.setImageDrawable(GifDrawable(root.context.assets, ex.image))
            setTimeType(ex)
        } else {
            imNextExercise.setImageDrawable(GifDrawable(root.context.assets, "finish.gif"))
            tvNext.text = getString(R.string.end)
        }
    } // показываем следующее упражнение


    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            binding.tvTimer.text = exercise.time
        } else {
            startTimer(exercise)
        }

    }//определяем как будет пмсатсья время

    private fun setTimeType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            val time = exercise.name + ": ${exercise.time}"
            binding.tvNext.text = time
        } else {
            val time = exercise.name + ": ${TimeUtils.getTime(exercise.time.toLong() * 1000)}"
            binding.tvNext.text = time
        }
    } //определяем как будет пмсатсья время

    private fun startTimer(exercise: ExerciseModel) = with(binding) {
        pBar.max = exercise.time.toInt() * 1000

        // Отменяем предыдущий таймер, если он был запущен
        timer?.cancel()

        // Создаем новый таймер
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 10) {
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                pBar.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    } //Включаем таймер и бар


    override fun onDetach() {
        super.onDetach()

        model.savePref(currentDay.toString(), exerciseCounter - 1) //записывает в спислк
        timer?.cancel()// если свернули приложение останавливаем таймер
    }


    companion object {

        @JvmStatic
        fun newInstance() = ExerciseFragment()

    }
}

