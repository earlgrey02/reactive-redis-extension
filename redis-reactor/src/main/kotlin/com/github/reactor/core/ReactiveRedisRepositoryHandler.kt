package com.github.reactor.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.reactor.repository.ReactiveRedisRepositoryProxy
import org.reactivestreams.Publisher
import org.springframework.data.redis.core.ReactiveRedisTemplate
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.time.Duration

/**
 * [InvocationHandler] used to proxy for [com.github.reactor.repository.ReactiveRedisRepository].
 *
 * @param entityType
 * @param redisTemplate Template to perform Redis-related functions.
 * @param objectMapper A mapper that performs serialization and deserialization in Redis-related functions.
 */
class ReactiveRedisRepositoryHandler(
    entityType: Class<*>,
    redisTemplate: ReactiveRedisTemplate<String, String>,
    objectMapper: ObjectMapper
) : InvocationHandler {
    private val repositoryProxy = ReactiveRedisRepositoryProxy(
        entityType = entityType,
        redisTemplate = redisTemplate,
        objectMapper = objectMapper
    )

    override fun invoke(proxy: Any, method: Method, args: Array<*>): Publisher<*> =
        when (method.name) {
            "findByKey" -> repositoryProxy.findByKey(args[0].toString())
            "save" -> {
                val value = args[0] as Any

                when (args.size) {
                    1 -> repositoryProxy.save(value)
                    2 -> repositoryProxy.save(value, args[1] as Duration)
                    else -> throw UnsupportedOperationException()
                }
            }
            "deleteByKey" -> repositoryProxy.deleteByKey(args[0].toString())
            else -> throw UnsupportedOperationException()
        }
}
