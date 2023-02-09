package com.javimartd.theguardian.v2.data.datasources.remote.common.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseSourceInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        if (response.cacheResponse != null) {
            Log.i(ResponseSourceInterceptor::class.java.name, "Cache")
        } else {
            Log.i(ResponseSourceInterceptor::class.java.name, "Network")
        }
        return response
    }
}