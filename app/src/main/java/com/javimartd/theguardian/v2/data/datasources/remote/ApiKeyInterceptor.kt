package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKey: String = BuildConfig.THE_GUARDIAN_API_KEY
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("api-key", apiKey)
            .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}