package com.fwhyn.app.gethub

import app.cash.turbine.test
import assertNoProducedValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun `test flowOf`() = runTest {
        val flowData: Flow<Int> = flowOf(1, 2, 3, 4, 5)

        flowData.test {
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            assertEquals(3, awaitItem())
            assertEquals(4, awaitItem())
            assertEquals(5, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test Flow`() = runTest {
        val flowData: Flow<Int> = flow {
            emit(1)
            emit(2)
        }

        // Collecting the flow multiple times should yield the same results
        flowData.test {
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            awaitComplete()
        }

        // Collecting the flow again should yield the same results
        flowData.test {
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test SharedFlow`() = runTest {
        val flowData: MutableSharedFlow<Int> = MutableSharedFlow()
        flowData.emit(1)
        flowData.emit(2)

        flowData.test {
            assertNoProducedValue()
        }
    }

    @Test
    fun `test SharedFlow and Flow if emit first, then collect`() = runTest {
        val sharedFlowData: MutableSharedFlow<Int> = MutableSharedFlow()
        val flowData: Flow<Int> = sharedFlowData

        sharedFlowData.emit(1)
        sharedFlowData.emit(2)

        flowData.test {
            assertNoProducedValue()
        }
    }

    @Test
    fun `test SharedFlow and Flow if collect first, then emit`() = runTest {
        val sharedFlowData: MutableSharedFlow<Int> = MutableSharedFlow()
        val flowData: Flow<Int> = sharedFlowData

        val job = backgroundScope.launch {
            flowData.test {
                assertEquals(1, awaitItem())
                assertEquals(2, awaitItem())
            }
        }

        delay(100)
        sharedFlowData.emit(1)
        sharedFlowData.emit(2)

        job.join() // Wait for the background job to complete

    }

    @Test
    fun `test StateFlow and Flow if emit first, then collect`() = runTest {
        val stateFlowData: MutableStateFlow<Int> = MutableStateFlow(0)
        val flowData: Flow<Int> = stateFlowData

        stateFlowData.emit(1)
        stateFlowData.emit(2)

        // Collecting the flow should yield the emitted values
        flowData.test {
            assertEquals(2, awaitItem())
        }

        // Collecting the flow again should yield the same results
        flowData.test {
            assertEquals(2, awaitItem())
        }
    }

    @Test
    fun `test StateFlow and Flow if collect first, then emit`() = runTest {
        val stateFlowData: MutableStateFlow<Int> = MutableStateFlow(0)
        val flowData: Flow<Int> = stateFlowData

        val job = backgroundScope.launch {
            flowData.test {
                assertEquals(0, awaitItem())
                assertEquals(1, awaitItem())
                assertEquals(2, awaitItem())
            }
        }

        delay(100)
        stateFlowData.emit(1)
        stateFlowData.emit(2)

        job.join() // Wait for the background job to complete
    }
}