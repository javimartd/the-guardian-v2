package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.data.common.ErrorTypes

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorTypes
}