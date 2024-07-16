package com.javimartd.theguardian.v2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class TheGuardianApp: Application() {

    // SupervisorJob is important because any exception in the coroutine doesn't lead to the
    // cancellation of the other coroutines that are started im this scope. The started coroutine
    // should not defeat each other.
    val applicationScope  = CoroutineScope(SupervisorJob())

    companion object {
        lateinit var instance: TheGuardianApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
    }
}