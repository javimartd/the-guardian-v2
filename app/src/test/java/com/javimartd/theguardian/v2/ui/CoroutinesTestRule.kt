package com.javimartd.theguardian.v2.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
): TestWatcher() {

    // Do something before starting your test
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    // Do something after finishing your test
    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}