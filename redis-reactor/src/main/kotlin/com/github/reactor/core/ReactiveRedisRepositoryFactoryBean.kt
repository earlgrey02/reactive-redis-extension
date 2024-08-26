package com.github.reactor.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.core.AbstractRepositoryFactoryBean
import com.github.reactor.repository.ReactiveRedisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import java.lang.reflect.InvocationHandler

/**
 * Reactive implementation of [AbstractRepositoryFactoryBean].
 * @see [AbstractRepositoryFactoryBean]
 */
class ReactiveRedisRepositoryFactoryBean<T : ReactiveRedisRepository<*, *>>(
    repositoryType: Class<T>
) : AbstractRepositoryFactoryBean<T>(repositoryType) {
    @Autowired
    private lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun getHandler(): InvocationHandler =
        ReactiveRedisRepositoryHandler(
            entityType = entityType,
            redisTemplate = redisTemplate,
            objectMapper = objectMapper
        )
}
