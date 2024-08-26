package com.github.reactor.core

import com.github.core.BeanRegistrationProvider
import com.github.reactor.annotation.EnableReactiveRedisRepositories
import com.github.reactor.repository.ReactiveRedisRepository
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata

/**
 * Entry point to register [ReactiveRedisRepository] as a bean.
 * @see [EnableReactiveRedisRepositories]
 */
class ReactiveRedisRepositoryRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        val provider =
            BeanRegistrationProvider(
                registry = registry,
                targetType = ReactiveRedisRepository::class.java,
                beanType = ReactiveRedisRepositoryFactoryBean::class.java
            )

        EnableReactiveRedisRepositories(importingClassMetadata)
            .basePackages
            .forEach(provider::findCandidateComponents)
    }
}
