package com.github.reactor.fixture

import com.github.core.fixture.Entity
import com.github.reactor.repository.ReactiveRedisRepository
import org.springframework.stereotype.Repository

@Repository
interface EntityRepository : ReactiveRedisRepository<Entity, String>
