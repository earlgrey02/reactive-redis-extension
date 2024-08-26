package com.github.coroutine.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import java.time.Duration

/**
 * Coroutine specification of [RedisRepository].
 * Query method is not supported.
 */
@NoRepositoryBean
interface CoroutineRedisRepository<V, K> : Repository<V, K> {
    suspend fun findByKey(key: K): V?

    suspend fun save(value: V): V

    suspend fun save(value: V, expire: Duration): V

    suspend fun deleteByKey(key: K): Boolean
}
