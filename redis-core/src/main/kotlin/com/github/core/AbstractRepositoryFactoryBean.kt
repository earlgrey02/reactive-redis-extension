package com.github.core

import org.springframework.beans.factory.FactoryBean
import org.springframework.data.repository.Repository
import java.lang.reflect.InvocationHandler
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy

/**
 * [FactoryBean] for [com.github.reactor.repository.ReactiveRedisRepository].
 * @see [FactoryBean]
 *
 * @param repositoryType Actual repository type that inherits [com.github.reactor.repository.ReactiveRedisRepository]
 */
abstract class AbstractRepositoryFactoryBean<T : Repository<*, *>>(
    private val repositoryType: Class<T>
) : FactoryBean<T> {
    protected val entityType by lazy {
        (repositoryType.genericInterfaces[0] as ParameterizedType)
            .actualTypeArguments
            .first() as Class<*>
    }

    /**
     * Abstract method that returns [InvocationHandler] that will vary depending on the technology(Reactor, Coroutine)
     *
     * @return [InvocationHandler] to be used in JDK Dynamic Proxy.
     */
    abstract fun getHandler(): InvocationHandler

    override fun getObject(): T =
        Proxy.newProxyInstance(
            repositoryType.classLoader,
            arrayOf(repositoryType),
            getHandler()
        ) as T

    override fun getObjectType(): Class<T> = repositoryType
}
