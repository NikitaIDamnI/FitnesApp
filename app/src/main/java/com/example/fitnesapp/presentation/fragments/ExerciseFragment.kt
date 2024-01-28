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
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false) // Надули вью
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar!!
        viewModel.completedExercises.observe(viewLifecycleOwner) {
            val title = "${it + 1} / ${viewModel.sizeExercise + 1}"
            ab.title = title
        }
        show()
    }

    private fun show() {
        binding.bNext.setOnClickListener {
            viewModel.nextExercise()
        }
        viewModel.finish.observe(viewLifecycleOwner) { finish ->
            if (finish) {
                showExercise()
                showNextExercise()
            } else {
                viewModel.nextExercise()
                FragmentManager.setFragment(
                    DayFinishFragment.newInstance(),
                    activity as AppCompatActivity
                )
            }
        }
    }

    private fun showExercise() = with(binding) {
        viewModel.thisExercise.observe(viewLifecycleOwner) { it ->
            imMainEx.setImageDrawable(it.image.let { GifDrawable(root.context.assets, it) })
            tvNameEx.text = it.name
        }
        viewModel.formatTime.observe(viewLifecycleOwner) {
            tvTimer.text = it
        }
        viewModel.progress.observe(viewLifecycleOwner) {
            pBar.progress = it
        }
    }

    private fun showNextExercise() = with(binding) {
        viewModel.nextExercise.observe(viewLifecycleOwner) {
            imNextExercise.setImageDrawable(GifDrawable(root.context.assets, it.image))
            tvNext.text = it.time
        }
    }


    private fun parseArgument(): DayModel {
        return (requireArguments().getParcelable<DayModel>(DAY_NUMBER) ?: throw RuntimeException(
            "ExercisesListFragment | thisDay == null"
        ))
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.update()
    }

    override fun onStop() {
        super.onStop()
        viewModel.update()
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


