package com.javimartd.theguardian.v2.data.datasources.remote.common

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCall(onExecute: suspend () -> Response<T>): Result<T> {
    return try {
        val response = onExecute.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.success(body)
        } else {
            Result.failure(HttpException(response))
        }
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
}