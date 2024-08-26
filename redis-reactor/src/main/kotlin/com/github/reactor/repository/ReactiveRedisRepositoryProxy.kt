package com.github.reactor.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.core.util.getKey
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.core.publisher.Mono
import java.time.Duration

/**
 * [ReactiveRedisRepository] implementation to be used for proxying.
 * @see [com.github.reactor.core.ReactiveRedisRepositoryHandler]
 */
@Suppress(
    "SpringDataMethodInconsistencyInspection",
    "SpringDataRepositoryMethodReturnTypeInspection"
)
class ReactiveRedisRepositoryProxy(
    private val entityType: Class<*>,
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : ReactiveRedisRepository<Any, String> {
    override fun findByKey(key: String): Mono<Any> =
        redisTemplate.opsForValue()
            .get(key)
            .map { objectMapper.readValue(it, entityType) }

    override fun save(value: Any): Mono<Any> =
        redisTemplate.opsForValue()
            .set(value.getKey(entityType), objectMapper.writeValueAsString(value))
            .thenReturn(value)

    override fun save(value: Any, expire: Duration): Mono<Any> =
        redisTemplate.opsForValue()
            .set(value.getKey(entityType), objectMapper.writeValueAsString(value), expire)
            .thenReturn(value)

    override fun deleteByKey(key: String): Mono<Boolean> =
        redisTemplate.opsForValue()
            .delete(key)
}
