package com.javimartd.theguardian.v2.utils

import com.javimartd.theguardian.v2.data.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDefaultDispatcher: DispatcherProvider {

    private val testDispatcher = TestCoroutineDispatcher()

    override fun main(): CoroutineDispatcher = testDispatcher
    override fun default(): CoroutineDispatcher = testDispatcher
    override fun unconfined(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
}