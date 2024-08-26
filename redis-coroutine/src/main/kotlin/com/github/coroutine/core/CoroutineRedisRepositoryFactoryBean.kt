package com.github.coroutine.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.core.AbstractRepositoryFactoryBean
import com.github.coroutine.repository.CoroutineRedisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import java.lang.reflect.InvocationHandler

/**
 * Coroutine variant of [com.github.reactor.core.ReactiveRedisRepositoryFactoryBean].
 */
class CoroutineRedisRepositoryFactoryBean<T : CoroutineRedisRepository<*, *>>(
    repositoryType: Class<T>
) : AbstractRepositoryFactoryBean<T>(repositoryType) {
    @Autowired
    private lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun getHandler(): InvocationHandler =
        CoroutineRedisRepositoryHandler(
            entityType = entityType,
            redisTemplate = redisTemplate,
            objectMapper = objectMapper
        )
}
