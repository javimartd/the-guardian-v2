package com.javimartd.theguardian.v2

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class TheGuardianApp: Application() {

    companion object {
        lateinit var instance: TheGuardianApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}