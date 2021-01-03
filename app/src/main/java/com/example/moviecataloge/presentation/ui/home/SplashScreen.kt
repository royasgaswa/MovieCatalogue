package com.example.moviecataloge.presentation.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.moviecataloge.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(object :Runnable{
            override fun run() {
                val intent=Intent(this@SplashScreen,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        },2000)
    }
}