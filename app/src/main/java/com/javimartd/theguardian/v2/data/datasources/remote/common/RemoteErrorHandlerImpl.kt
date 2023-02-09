package com.javimartd.theguardian.v2.data.datasources.remote.common

import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.common.ErrorTypes
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class RemoteErrorHandlerImpl @Inject constructor(): ErrorHandler {

    override fun getError(throwable: Throwable): ErrorTypes {
        return when(throwable) {
            is IOException -> ErrorTypes.RemoteErrors.Network
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorTypes.RemoteErrors.AccessDenied
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorTypes.RemoteErrors.Server
                    HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorTypes.RemoteErrors.UnAuthorized
                    else -> ErrorTypes.RemoteErrors.Unknown
                }
            }
            else -> ErrorTypes.RemoteErrors.Unknown
        }
    }
}