package com.javimartd.theguardian.v2.domain

interface Command<T> {
    fun execute(): T
}