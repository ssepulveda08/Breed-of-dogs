package com.example.breedofdogs.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.breedofdogs.R
import com.google.accompanist.pager.ExperimentalPagerApi

class SplashActivity : Activity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        val newActivity = Intent(this, MainActivity::class.java)
        startActivity(newActivity)
        finish()
        setContentView(R.layout.activity_splash)
    }
}