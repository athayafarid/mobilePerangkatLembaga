package com.example.perangkatlembaga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.perangkatlembaga.Message.tutorial.TutorialMessageActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            val onboardingFinished = sharedPref.getBoolean("onboardingFinished", false)

            when {
                isLogin -> {
                    startActivity(Intent(this, BaseActivity::class.java))
                }
                !onboardingFinished -> {
                    startActivity(Intent(this, TutorialMessageActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            finish()
        }, 2000) // 2 seconds delay
    }
}