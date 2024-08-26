package com.github.core.fixture

import com.github.core.annotation.Key
import org.springframework.data.redis.core.RedisHash

@RedisHash
data class Entity(
    @Key
    val key: String,
    val content: String
)
