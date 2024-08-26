package com.github.core.fixture

const val KEY = "earlgrey02"
const val CONTENT = "test"

fun createEntity(
    key: String = KEY,
    content: String = CONTENT
): Entity =
    Entity(
        key = key,
        content = content
    )
