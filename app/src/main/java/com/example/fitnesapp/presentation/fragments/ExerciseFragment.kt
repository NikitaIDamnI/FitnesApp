package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesapp.R
import com.example.fitnesapp.domain.models.ExerciseModel
import com.example.fitnesapp.databinding.ExerciseBinding
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.presentation.viewmodels.ExerciseViewModel
import com.example.fitnesapp.presentation.viewmodels.ExerciseViewModelFactory
import com.example.fitnesapp.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable


class ExerciseFragment : Fragment() {

    private lateinit var binding: ExerciseBinding// cоздали VB
    private lateinit var ab: ActionBar // инициализируем Экшен бар

    private val viewModelFactory by lazy {
        ExerciseViewModelFactory(requireActivity().application, parseArgument())
    }
    private val viewModel: ExerciseViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExerciseViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseBinding.inflate(inflater, container, false) // Надули вью
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ab = (activity as AppCompatActivity).supportActionBar!!

        show()
        binding.bNext.setOnClickListener {
            viewModel.nextExercise()
        }
    }


    private fun show() {
        viewModel.finish.observe(viewLifecycleOwner) { finish ->
        if (finish) {
            viewModel.thisExercise.observe(viewLifecycleOwner) {
                showExercise(it)
            }
            showNextExercise()
        } else {
            viewModel.nextExercise()
            FragmentManager.setFragment(
                DayFinishFragment.newInstance(),
                activity as AppCompatActivity
            )  // конец упражнений пере
        }
    }

    } //TODO перенести во (ViewModel)

    private fun showExercise(exercise: ExerciseModel) = with(binding) {
        imMainEx.setImageDrawable(exercise.image.let { GifDrawable(root.context.assets, it) })
        tvNameEx.text = exercise.name
        viewModel.completedExercises.observe(viewLifecycleOwner) {
            val title = "${it+1} / ${viewModel.sizeExercise + 1}"
            ab.title = title
        }
        viewModel.formatTime.observe(viewLifecycleOwner) {
            tvTimer.text = it
        }
        viewModel.progress.observe(viewLifecycleOwner) {
            pBar.progress = it
        }

    } //показываеи упражнение(Oставить)


    private fun showNextExercise() = with(binding) {
        viewModel.finish.observe(viewLifecycleOwner) { finish->
        if (finish) {

            viewModel.nextExercise.observe(viewLifecycleOwner) {
                imNextExercise.setImageDrawable(GifDrawable(root.context.assets, it.image))
            }
            viewModel.nextExercise.observe(viewLifecycleOwner) {
                tvNext.text = it.time
            }
        } else {
            imNextExercise.setImageDrawable(GifDrawable(root.context.assets, "finish.gif"))
            tvNext.text = getString(R.string.end)
        }
    }
    } // показываем следующее упражнение(Оставить)


/*
    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            binding.tvTimer.text = exercise.time
        } else {
            //startTimer(exercise)
        }
       }
 */



    //определяем как будет пмсатсья время

   /* private fun setTimeType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            val time = exercise.name + ": ${exercise.time}"
            binding.
        } else {
            val time = exercise.name + ": ${TimeUtils.getTime(exercise.time.toLong() * 1000)}"
            binding.tvNext.text = time
        }
    } //определяем как будет пмсатсья время

    */


    /*
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

     */


    private fun parseArgument(): DayModel {
        return (requireArguments().getParcelable<DayModel>(DAY_NUMBER) ?: throw RuntimeException(
            "ExercisesListFragment | thisDay == null"
        ))
    }

    companion object {

        fun newInstance(dayModel: DayModel): ExerciseFragment {
            return ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DAY_NUMBER, dayModel)

                }
            }
        }

        private const val DAY_NUMBER = "dayNumber"


    }
}

