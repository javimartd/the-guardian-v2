package com.javimartd.theguardian.v2.data.datasources.remote

interface RemoteMapper<R, D> {
    fun mapFromRemote(remote: R): D
}