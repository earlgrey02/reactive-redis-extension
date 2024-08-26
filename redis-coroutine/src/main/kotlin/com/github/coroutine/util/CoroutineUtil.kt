package com.github.coroutine.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

/**
 * A utility method that allows you to call a suspend function
 * by creating a coroutine scope from a regular function without the suspend keyword.
 * @see [Continuation]
 */
internal fun <T> Continuation<T>.coroutineScope(block: suspend () -> T): Any =
    with(CoroutineScope(context)) {
        launch {
            runCatching { block() }
                .run(::resumeWith)
        }

        COROUTINE_SUSPENDED
    }
