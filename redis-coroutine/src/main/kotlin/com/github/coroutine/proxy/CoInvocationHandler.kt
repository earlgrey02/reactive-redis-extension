package com.github.coroutine.proxy

import com.github.coroutine.util.coroutineScope
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.coroutines.Continuation
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.kotlinFunction

/**
 * Coroutine variant of [InvocationHandler].
 */
abstract class CoInvocationHandler : InvocationHandler {
    final override fun invoke(proxy: Any, method: Method, args: Array<*>?): Any? =
        with(method.kotlinFunction!!) {
            val parameters = args?.toList() ?: emptyList()

            if (isSuspend) {
                parameters.getContinuation<Any?>()
                    .coroutineScope { coInvoke(this, parameters.withoutContinuation()) }
            } else invoke(this, parameters)
        }

    abstract suspend fun coInvoke(function: KFunction<*>, parameters: List<*>): Any?

    abstract fun invoke(function: KFunction<*>, parameters: List<*>): Any?

    private fun <T> List<*>.getContinuation(): Continuation<T> = last { it is Continuation<*> } as Continuation<T>

    private fun List<*>.withoutContinuation(): List<*> = take(size - 1)
}
