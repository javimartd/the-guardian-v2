package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.data.state.ErrorTypes

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorTypes
}