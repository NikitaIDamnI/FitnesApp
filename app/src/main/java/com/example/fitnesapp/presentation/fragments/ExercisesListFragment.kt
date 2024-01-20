package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.presentation.adapters.ExerciseAdapter
import com.example.fitnesapp.databinding.ExercisesListFragmentBinding
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel


class ExercisesListFragment : Fragment() {
    private lateinit var binding: ExercisesListFragmentBinding // cоздали VB
    private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels()
    private lateinit var ab: ActionBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExercisesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingAdapter()
        ab = (activity as AppCompatActivity).supportActionBar!!
        ab.title = getString(R.string.exercises)

        model.mutableLiveExercise.observe(viewLifecycleOwner) {
            //for (i in 0 until model.getExerciseCount()) {
             //   it[i] = it[i].copy(isDone = true)
            //}//Todo со
            adapter.submitList(it)
        } //

        binding.bStart.setOnClickListener {
            FragmentManager.setFragment(
                WaitingFragment.newInstance(),
                activity as AppCompatActivity
            )
        }


    }

    private fun bindingAdapter() = with(binding) {
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
    }

    companion object {

        fun newInstance(dayNumber:Int): ExercisesListFragment {
            return ExercisesListFragment().apply {
                arguments = Bundle().apply {
                    putInt(DAY_NUMBER,dayNumber)
                }
            }

        }

        private const val DAY_NUMBER = "dayNumber"

    }
}