package com.fwhyn.app.gethub.common.helper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun <T> MutableSharedFlow<T>.emitEvent(scope: CoroutineScope, data: T) {
    scope.launch {
        emit(data)
    }
}