package com.github.coroutine.annotation

import com.github.coroutine.core.CoroutineRedisRepositoryRegistrar
import org.springframework.context.annotation.Import
import org.springframework.core.type.AnnotationMetadata
import java.lang.annotation.Inherited

/**
 * Coroutine variant of [com.github.reactor.annotation.EnableReactiveRedisRepositories].
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@Import(CoroutineRedisRepositoryRegistrar::class)
annotation class EnableCoroutineRedisRepositories(
    val basePackages: Array<String> = []
) {
    companion object {
        operator fun invoke(annotationMetadata: AnnotationMetadata): EnableCoroutineRedisRepositories =
            EnableCoroutineRedisRepositories(
                annotationMetadata.getAnnotationAttributes(EnableCoroutineRedisRepositories::class.java.name)
                    ?.get(EnableCoroutineRedisRepositories::basePackages.name) as Array<String>?
                    ?: emptyArray()
            )
    }
}
