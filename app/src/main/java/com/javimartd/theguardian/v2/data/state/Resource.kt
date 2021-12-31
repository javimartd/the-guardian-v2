package com.javimartd.theguardian.v2.data.state

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: ErrorTypes) : Resource<Nothing>()
}