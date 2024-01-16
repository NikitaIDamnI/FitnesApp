package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.WaitingFragmentBinding
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.TimeUtils


const val COUNT_DOWN_TIMER: Long = 10000L
class WaitingFragment : Fragment() {
    private lateinit var binding: WaitingFragmentBinding // cоздали VB
    private lateinit var timer: CountDownTimer
    private lateinit var ab: ActionBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WaitingFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pBar.max = COUNT_DOWN_TIMER.toInt()
        ab = (activity as AppCompatActivity).supportActionBar!!
        ab.title = getString( R.string.waiting)
        startTimer()
    }


    private fun startTimer() = with(binding) {
        timer = object : CountDownTimer(COUNT_DOWN_TIMER , 10) {
            override fun onTick(restTime: Long) {
                pBar.progress= restTime.toInt()
                tvTimer.text = TimeUtils.getTime(restTime)

            }
            override fun onFinish() {
                FragmentManager.setFragment(
                    ExerciseFragment.newInstance(),
                    activity as AppCompatActivity)
            }
        }.start()
    }

    override fun onDetach() { //при сворачивании приложения таймер останавливается
        super.onDetach()
        timer.cancel()
    }


    companion object {

        @JvmStatic
        fun newInstance() = WaitingFragment()

    }
}