package com.skogen.coin.screens.splash

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import com.skogen.coin.App
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity

/**
 * Created by Koba on 20.11.2018.
 * cosmicgate97@gmail.com
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(App.context, LoginActivity::class.java))
        finish()
    }
}