package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.data.remote.RetrofitBuilder
import com.javimartd.theguardian.v2.data.NewsDataSource
import com.javimartd.theguardian.v2.data.remote.NewsRemoteDataSource

object ServiceLocator {
    fun getTheGuardianApi(): NewsDataSource = NewsRemoteDataSource(
        RetrofitBuilder.apiService
    )
}