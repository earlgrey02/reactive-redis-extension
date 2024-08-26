package com.github.coroutine

import com.github.core.config.JacksonConfiguration
import com.github.core.config.RedisTestConfiguration
import com.github.coroutine.annotation.EnableCoroutineRedisRepositories
import org.springframework.test.context.ContextConfiguration

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@EnableCoroutineRedisRepositories(basePackages = ["com.github"])
@ContextConfiguration(
    classes = [
        RedisTestConfiguration::class,
        JacksonConfiguration::class
    ]
)
annotation class CoroutineRedisTest
