package com.fwhyn.app.gethub

import app.cash.turbine.ReceiveTurbine
import com.fwhyn.lib.baze.common.domain.helper.Rezult
import org.junit.Assert

suspend fun <T> ReceiveTurbine<T>.assertNoProducedValue() {
    try {
        val data = awaitItem()
        Assert.assertNull(data)
    } catch (e: Throwable) {
        println("Turbine: ${e.message}")
        Assert.assertNotNull("No value produced in 3s", e.message)
    }
}

suspend fun <T> ReceiveTurbine<T>.asResultFailureOrNull(): Rezult.Failure<Throwable>? {
    return awaitItem() as? Rezult.Failure<Throwable>
}