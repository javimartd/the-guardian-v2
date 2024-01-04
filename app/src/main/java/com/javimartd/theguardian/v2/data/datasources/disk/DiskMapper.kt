package com.javimartd.theguardian.v2.data.datasources.disk

interface DiskMapper<L, D> {
    fun mapFromLocal(local: L): D
    fun mapToLocal(data: D): L
}