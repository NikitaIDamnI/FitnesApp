package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import android.util.Log
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

                        model.resetProgress()
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
        model.listDays.observe(viewLifecycleOwner) {
            Log.d("LogListDay", "$it")
            adapter.submitList(it)
        }
    }

    private fun updateLeftDays() {
        model.leftDays.observe(viewLifecycleOwner) {
            binding.tvRestDays.text = it
        }
        model.dayPassed.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()

    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {

            FragmentManager.setFragment(
                ExercisesListFragment.newInstance(day.dayNumber),
                activity as AppCompatActivity
            )
        } else {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_massage,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.updateExercise(day, DayModel.UNDEFINED_COMPLETED_EXERCISES)
                        FragmentManager.setFragment(
                            ExercisesListFragment.newInstance(day.dayNumber),
                            activity as AppCompatActivity
                        )

                    }
                })

        }
    } //TODO обработать
}