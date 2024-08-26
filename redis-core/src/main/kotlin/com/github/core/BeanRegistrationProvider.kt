package com.github.core

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter

/**
 * A class that scans for a specific type and registers [FactoryBean] with that type.
 * @see [ClassPathScanningCandidateComponentProvider]
 *
 * @param registry [BeanDefinitionRegistry]
 * @param targetType Type to scan.
 * @param beanType Type of [FactoryBean] to be registered as a bean.
 */
class BeanRegistrationProvider(
    private val registry: BeanDefinitionRegistry,
    private val targetType: Class<*>,
    private val beanType: Class<out FactoryBean<*>>
) : ClassPathScanningCandidateComponentProvider(false) {
    init {
        addIncludeFilter(AssignableTypeFilter(targetType))
    }

    override fun isCandidateComponent(beanDefinition: AnnotatedBeanDefinition): Boolean =
        super.isCandidateComponent(beanDefinition) || beanDefinition.metadata.isInterface

    override fun findCandidateComponents(basePackage: String): MutableSet<BeanDefinition> =
        super.findCandidateComponents(basePackage)
            .onEach {
                val beanDefinition = it as GenericBeanDefinition
                val beanClassName = beanDefinition.beanClassName
                val factoryBeanDefinition = GenericBeanDefinition()
                    .apply {
                        setBeanClass(beanType)
                        constructorArgumentValues.addGenericArgumentValue(Class.forName(beanClassName))
                    }

                registry.registerBeanDefinition(beanClassName!!, factoryBeanDefinition)
            }
}
