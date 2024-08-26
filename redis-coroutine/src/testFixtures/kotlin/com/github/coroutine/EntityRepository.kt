package com.github.coroutine

import com.github.core.fixture.Entity
import com.github.coroutine.repository.CoroutineRedisRepository
import org.springframework.stereotype.Repository

@Repository
interface EntityRepository : CoroutineRedisRepository<Entity, String>
