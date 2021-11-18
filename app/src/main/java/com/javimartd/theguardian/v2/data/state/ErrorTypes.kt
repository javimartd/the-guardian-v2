package com.javimartd.theguardian.v2.data.state

sealed class ErrorTypes {
    sealed class RemoteErrors: ErrorTypes() {
        object ApiStatus: RemoteErrors()
        object Network: RemoteErrors()
        object Server: RemoteErrors()
        object AccessDenied: RemoteErrors()
        object Unknown: RemoteErrors()
    }
    sealed class LocalErrors: ErrorTypes() {
        // Does not required a this moment since we save data in memory
    }

}
