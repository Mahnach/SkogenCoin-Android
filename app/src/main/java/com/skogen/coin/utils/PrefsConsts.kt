package com.skogen.coin.utils

import com.pixplicity.easyprefs.library.Prefs


/**
 * Created by Koba on 26.11.2018.
 * cosmicgate97@gmail.com
 */
class PrefsConsts {
    companion object {
        const val USER_TOKEN = "user_token"
        fun isUserLoggedIn(): Boolean = !Prefs.getString(USER_TOKEN, "").isNullOrEmpty()
    }
}