package com.github.reactor.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import reactor.core.publisher.Mono
import java.time.Duration

/**
 * Reactive specification of [RedisRepository].
 * Query method is not supported.
 */
@NoRepositoryBean
interface ReactiveRedisRepository<V, K> : Repository<V, K> {
    fun findByKey(key: K): Mono<V>

    fun save(value: V): Mono<V>

    fun save(value: V, expire: Duration): Mono<V>

    fun deleteByKey(key: K): Mono<Boolean>
}
