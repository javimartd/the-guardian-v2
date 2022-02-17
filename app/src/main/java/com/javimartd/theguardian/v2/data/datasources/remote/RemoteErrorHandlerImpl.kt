package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.state.ErrorTypes
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
                    else -> ErrorTypes.RemoteErrors.Unknown
                }
            }
            else -> ErrorTypes.RemoteErrors.Unknown
        }
    }
}