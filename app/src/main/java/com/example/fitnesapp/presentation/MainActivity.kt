package com.example.fitnesapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesapp.R
import com.example.fitnesapp.presentation.fragments.DaysFragment
import com.example.fitnesapp.presentation.viewmodels.DaysFragmentViewModel
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // model.pref = getSharedPreferences("main", MODE_PRIVATE)
        FragmentManager.setFragment(DaysFragment.newInstance(),this) //выводим фрагмент
    }

    override fun onBackPressed() { // закрывет приложение если это главный фрагмент, если не  главный то на него переносит
        if(FragmentManager.currentFragment is DaysFragment) {
            super.onBackPressed()
        }else{
            FragmentManager.setFragment(DaysFragment.newInstance(),this)
        }
    }
}