package com.example.fitnesapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnesapp.R

object FragmentManager { // создаем функции которые часто повторяются

    var currentFragment: Fragment?=null // чтоб когда мы нажимаем кнопку назад, приложение не закрывалось

    fun setFragment(newFragment:Fragment,act:AppCompatActivity){ // Фyнкция постоянно меняет фрагменты
        val transaction = act.supportFragmentManager.beginTransaction()// Помогает переносить фрагменты
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
        transaction.replace(R.id.placeHolder,newFragment) // меняет в в мейнАктивити лояут на фрагмент
        transaction.commit() // Применяет изменения

        currentFragment = newFragment
    }



}