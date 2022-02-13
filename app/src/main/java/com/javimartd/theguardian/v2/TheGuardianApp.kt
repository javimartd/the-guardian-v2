package com.javimartd.theguardian.v2

import android.app.Application
import com.javimartd.theguardian.v2.ui.di.ServiceLocator

class TheGuardianApp: Application() {
    val serviceLocator = ServiceLocator()
}