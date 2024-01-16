package com.example.fitnesapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DayFinishBinding
import com.example.fitnesapp.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable


class DayFinishFragment : Fragment() {
    private lateinit var binding: DayFinishBinding // cоздали VB
    private lateinit var ab: ActionBar // инициализируем Экшен бар


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DayFinishBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar!!
        ab.title = getString(R.string.done)
        init()
    }

    private fun init() = with(binding){
        imFinishGif.setImageDrawable(GifDrawable(root.context.assets,"finish.gif"))
        bEnd.setOnClickListener {

            FragmentManager.setFragment(
                DaysFragment.newInstance(),
                activity as AppCompatActivity)

        }
    } // показывает gif  и нажимает кнопку выйти на начало


    companion object {

        @JvmStatic
        fun newInstance() = DayFinishFragment()

    }
}