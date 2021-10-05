package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.data.datasources.remote.RetrofitBuilder
import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSourceImpl

object ServiceLocator {
    private fun getTheGuardianApi() = RetrofitBuilder.apiService

    private fun getRemoteDataSource(): RemoteDataSource {
        return RemoteDataSourceImpl(getTheGuardianApi())
    }

    private fun getLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }

    fun getRepository(): Repository {
        return RepositoryImpl(
            remoteDataSource = getRemoteDataSource(),
            localDataSource = getLocalDataSource()
        )
    }
}