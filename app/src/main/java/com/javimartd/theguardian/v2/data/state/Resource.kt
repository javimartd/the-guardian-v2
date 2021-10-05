package com.javimartd.theguardian.v2.data.state

sealed class Resource<T>(val data: T? = null,
                         val error: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(e: Throwable) : Resource<T>(null, e)
}
