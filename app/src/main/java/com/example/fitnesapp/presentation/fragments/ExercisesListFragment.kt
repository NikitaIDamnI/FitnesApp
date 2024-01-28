package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.presentation.adapters.exerciseAdapter.ExerciseAdapter
import com.example.fitnesapp.databinding.ExercisesListFragmentBinding
import com.example.fitnesapp.domain.models.DayModel
import com.example.fitnesapp.presentation.viewmodels.ExercisesListFragmentViewModel
import com.example.fitnesapp.utils.FragmentManager


class ExercisesListFragment : Fragment() {
    private lateinit var binding: ExercisesListFragmentBinding // cоздали VB
    private lateinit var adapter: ExerciseAdapter
    private val model: ExercisesListFragmentViewModel by lazy {
        ViewModelProvider(this)[ExercisesListFragmentViewModel::class.java]
    }
    private lateinit var thisDay: DayModel
    private lateinit var ab: ActionBar
    //  private var dayNumber by Delegates.notNull<Int>()


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
        parseArgument()
        Log.d("ExercisesListFragment", "day $thisDay")
        // Log.d("ExercisesListFragment","$dayNumber")
        val list = model.getList(thisDay)
        adapter.submitList(list)


        binding.bStart.setOnClickListener {
            FragmentManager.setFragment(
                WaitingFragment.newInstance(thisDay),
                activity as AppCompatActivity
            )
        }


    }

    private fun parseArgument() {
        thisDay = requireArguments().getParcelable<DayModel>(DAY_NUMBER) ?:
        throw RuntimeException("ExercisesListFragment | thisDay == null")
    }


    private fun bindingAdapter() = with(binding) {
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
    }

    companion object {

        fun newInstance(day: DayModel): ExercisesListFragment {
            return ExercisesListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DAY_NUMBER, day)
                }
            }

        }

        private const val DAY_NUMBER = "dayNumber"

    }
}