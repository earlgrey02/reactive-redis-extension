package com.github.coroutine.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.coroutine.proxy.CoInvocationHandler
import com.github.reactor.repository.ReactiveRedisRepositoryProxy
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import java.time.Duration
import kotlin.reflect.KFunction

/**
 * Coroutine variant of [com.github.reactor.core.ReactiveRedisRepositoryHandler].
 */
class CoroutineRedisRepositoryHandler(
    entityType: Class<*>,
    redisTemplate: ReactiveRedisTemplate<String, String>,
    objectMapper: ObjectMapper
) : CoInvocationHandler() {
    private val repositoryProxy = ReactiveRedisRepositoryProxy(
        entityType = entityType,
        redisTemplate = redisTemplate,
        objectMapper = objectMapper
    )

    override suspend fun coInvoke(function: KFunction<*>, parameters: List<*>): Any? =
        when (function.name) {
            "findByKey" -> repositoryProxy.findByKey(parameters[0].toString())
                .awaitSingleOrNull()
            "save" -> {
                val value = parameters[0] as Any

                when (parameters.size) {
                    1 -> repositoryProxy.save(value)
                        .awaitSingle()
                    2 -> repositoryProxy.save(value, parameters[1] as Duration)
                        .awaitSingleOrNull()
                    else -> throw UnsupportedOperationException()
                }
            }
            "deleteByKey" -> repositoryProxy.deleteByKey(parameters[0].toString())
                .awaitSingle()
            else -> throw UnsupportedOperationException()
        }

    override fun invoke(function: KFunction<*>, parameters: List<*>): Any? =
        when (function.name) {
            else -> throw UnsupportedOperationException()
        }
}
