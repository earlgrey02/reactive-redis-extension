package com.github.reactor.fixture

import com.github.core.config.JacksonConfiguration
import com.github.core.config.RedisTestConfiguration
import com.github.reactor.annotation.EnableReactiveRedisRepositories
import org.springframework.test.context.ContextConfiguration

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@EnableReactiveRedisRepositories(basePackages = ["com.github"])
@ContextConfiguration(
    classes = [
        RedisTestConfiguration::class,
        JacksonConfiguration::class
    ]
)
annotation class ReactiveRedisTest
