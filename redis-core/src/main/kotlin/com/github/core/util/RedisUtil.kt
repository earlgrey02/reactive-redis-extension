package com.github.core.util

import com.github.core.annotation.Key

/**
 * Utility method to get the value of the field corresponding to [Key] through reflection.
 *
 * @param entityClass Entity type to retrieve key.
 */
fun Any.getKey(entityClass: Class<*>) =
    entityClass.declaredFields
        .first { it.isAnnotationPresent(Key::class.java) }
        .apply { setAccessible(true) }
        .get(this)
        .toString()
