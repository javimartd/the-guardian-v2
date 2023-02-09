package com.javimartd.theguardian.v2.data.common

sealed class ErrorTypes: Throwable() {
    sealed class RemoteErrors: ErrorTypes() {
        object ApiStatus: RemoteErrors()
        object Network: RemoteErrors()
        object Server: RemoteErrors()
        object UnAuthorized: RemoteErrors()
        object AccessDenied: RemoteErrors()
        object Unknown: RemoteErrors()
    }
    sealed class LocalErrors: ErrorTypes() {
        // Does not required a this moment
    }

}
