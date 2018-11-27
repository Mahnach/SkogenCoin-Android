package com.skogen.coin.screens.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skogen.coin.App
import com.skogen.coin.R
import com.skogen.coin.screens.login.LoginActivity
import com.skogen.coin.screens.main.MainActivity
import com.skogen.coin.utils.PrefsConsts

/**
 * Created by Koba on 20.11.2018.
 * cosmicgate97@gmail.com
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (PrefsConsts.isUserLoggedIn())
            startActivity(Intent(App.context, MainActivity::class.java))
        else
            startActivity(Intent(App.context, LoginActivity::class.java))
        finish()
    }
}