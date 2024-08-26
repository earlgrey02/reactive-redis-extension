package com.github.core.annotation

/**
 * Annotation that specifies the field that will be the key in [com.github.reactor.repository.ReactiveRedisRepository].
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Key
