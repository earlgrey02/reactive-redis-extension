package com.github.coroutine.core

import com.github.core.BeanRegistrationProvider
import com.github.coroutine.annotation.EnableCoroutineRedisRepositories
import com.github.coroutine.repository.CoroutineRedisRepository
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata

/**
 * Coroutine variant of [com.github.reactor.core.ReactiveRedisRepositoryRegistrar].
 */
class CoroutineRedisRepositoryRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        val provider =
            BeanRegistrationProvider(
                registry = registry,
                targetType = CoroutineRedisRepository::class.java,
                beanType = CoroutineRedisRepositoryFactoryBean::class.java
            )

        EnableCoroutineRedisRepositories(importingClassMetadata)
            .basePackages
            .forEach(provider::findCandidateComponents)
    }
}
