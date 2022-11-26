package com.javimartd.theguardian.v2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {

    // Do something before starting your test
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    // Do something after finishing your test
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
