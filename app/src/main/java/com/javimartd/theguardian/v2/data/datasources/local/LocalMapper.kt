package com.javimartd.theguardian.v2.data.datasources.local

interface LocalMapper<L, D> {
    fun mapFromLocal(local: L): D
    fun mapToLocal(data: D): L
}