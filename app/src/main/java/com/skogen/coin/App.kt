package com.skogen.coin

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

        Timber.plant(Timber.DebugTree())
    }

    companion object {

        @Volatile
        private var instance: App? = null

        val context: Context
            get() = instance!!.applicationContext
    }

}
