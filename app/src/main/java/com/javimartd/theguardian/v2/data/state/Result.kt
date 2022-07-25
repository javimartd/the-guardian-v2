package com.javimartd.theguardian.v2.data.state

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: ErrorTypes) : Result<Nothing>()
}