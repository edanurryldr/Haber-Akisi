package com.edanuryildirimappcent.newsapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edanuryildirimappcent.newsapp.R
import com.edanuryildirimappcent.newsapp.view.ui.MainActivity
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                    val intent = Intent(
                        baseContext,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }

        }
        background.start()
    }
}