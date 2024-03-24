package com.example.sweatmovies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
interface ViewModelTest {
    fun runUnconfinedTest(
        testBlock: (TestScope) -> Unit
    ) = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        testBlock.invoke(this)

        Dispatchers.resetMain()
    }

}