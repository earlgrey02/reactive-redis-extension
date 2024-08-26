package com.github.reactor.annotation

import com.github.reactor.core.ReactiveRedisRepositoryRegistrar
import org.springframework.context.annotation.Import
import org.springframework.core.type.AnnotationMetadata
import java.lang.annotation.Inherited

/**
 * Annotation that specifies the base package to scan for [com.github.reactor.repository.ReactiveRedisRepository].
 *
 * @param basePackages
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@Import(ReactiveRedisRepositoryRegistrar::class)
annotation class EnableReactiveRedisRepositories(
    val basePackages: Array<String> = []
) {
    companion object {
        operator fun invoke(annotationMetadata: AnnotationMetadata): EnableReactiveRedisRepositories =
            EnableReactiveRedisRepositories(
                annotationMetadata.getAnnotationAttributes(EnableReactiveRedisRepositories::class.java.name)
                    ?.get(EnableReactiveRedisRepositories::basePackages.name) as Array<String>?
                    ?: emptyArray()
            )
    }
}
