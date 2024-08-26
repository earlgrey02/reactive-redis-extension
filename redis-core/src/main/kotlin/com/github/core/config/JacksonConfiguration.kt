package com.github.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

/**
 * Auto configuration class for [ObjectMapper].
 */
@AutoConfiguration
class JacksonConfiguration {
    @ConditionalOnMissingBean(ObjectMapper::class)
    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .apply {
                registerModules(kotlinModule(), JavaTimeModule())
                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            }
}
