package com.example.fitnesapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.fitnesapp.R

@SuppressLint("CustomSplashScreen") //Подавляем ошибку чтобы она не появилась
class SplashActivity : AppCompatActivity() {   // Вызываем класс Сплешь Скрин , это наш загрузочеый экран

    private lateinit var timer : CountDownTimer // Пременная для таймера

    override fun onCreate(savedInstanceState: Bundle?) { // это наш цикл жизни (все вырисовывает)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // вырисовывает разметку
        timer = object : CountDownTimer(2000,1000){ // Инициальзируем таймер (это 2 секунды, это интервал межды ними, ровняется 1 секунде )

            override fun onTick(millisUntilFinished: Long) {
               /*
               Этот метод делает какие либо действия каждый тик
               у нас это может делать 2 раза за каждую секунду но нам это не нужно
               по этому мы это искользовать не будем
                */
            }

            override fun onFinish() {
               startActivity(Intent(this@SplashActivity, MainActivity::class.java)) // После того как счетчик закончится откроется Activity
            }

        }.start()
        
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel() // Прооблема  -> если пользователь выйдет пока грузится, счетчик не отстановится , эта функция решает проблему
    }
}