package com.javimartd.theguardian.v2.ui

import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.*

object ServiceLocator {
    private fun getTheGuardianApi() = RetrofitBuilder.apiService

    fun getRepository(): Repository {
        return RepositoryImpl(
            remoteDataSource = getRemoteDataSource(),
            localDataSource = getLocalDataSource()
        )
    }

    private fun getRemoteDataSource(): RemoteDataSource {
        return RemoteDataSourceImpl(
            getTheGuardianApi(),
            getErrorHandler()
        )
    }

    private fun getLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }

    private fun getErrorHandler(): ErrorHandler {
        return RemoteErrorHandlerImpl()
    }
}