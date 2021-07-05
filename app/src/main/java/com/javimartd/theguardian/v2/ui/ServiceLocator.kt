package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.data.RetrofitBuilder
import com.javimartd.theguardian.v2.data.TheGuardianApi
import com.javimartd.theguardian.v2.data.TheGuardianApiImpl

object ServiceLocator {
    fun getTheGuardianApi(): TheGuardianApi = TheGuardianApiImpl(
        RetrofitBuilder.apiService
    )
}