package com.github.core.util

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisTemplate

suspend fun <K, V> ReactiveRedisTemplate<K, V>.awaitFlush() {
    connectionFactory
        .reactiveConnection
        .serverCommands()
        .flushAll()
        .awaitSingle()
}
