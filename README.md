# Reactive Redis Extension

> **Reactive extension of `RedisRepository` used in Spring Data Redis**

## Description

Spring Data Reactive Redis does not support the reactive variant of the existing `RedisRepository`.
So, there is the inconvenience of having to write repetitive code for each entity.
We would like to solve this problem by implementing our own `ReactiveRedisRepository`.

Like Spring Data, this repository automatically implements functions for searching, saving, and deleting Redis as long
as it inherits `ReactiveRedisRepository`.
In addition, `CoroutineRedisRepository` for coroutines is also provided in the same specification.

## How to use

**1. Define the configuration class.**

```kotlin
@EnableReactiveRedisRepositories(basePackages = ["com.github"])
@Configuration
class ReactiveRedisConfiguration
```

Scan `ReactiveRedisRepository` only under the package corresponding to `basePackages`
of `@EnableReactiveRedisRepositories` and register the implementation as a bean.

In the case of coroutines, functions such as `@EnableReactiveRedisRepositories` can be used
through `@EnableCoroutineRedisRepository`.

**2. Define entity and repository.**

```kotlin
@RedisHash
data class RefreshToken(
    @Key
    val username: String,
    val content: String
)
```

```kotlin
interface RefreshTokenRepository : ReactiveRedisRepository<RefreshToken, String>
```

The field corresponding to `@Key` in the entity becomes the key to be used when saving in Redis.
The value actually stored is a JSON(JavaScript Object Notation) string for the entity.

**3. Inject and use the repository.**

```kotlin
@Service
class UserService(
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun refresh() {
        val refreshToken = RefreshToken(
            username = "earlgrey02",
            content = createToken()
        )

        refreshTokenRepository.save(refreshToken, Duration.ofMinutes(30))
    }
}
```

Since the implementation is internally registered as a bean through the runtime proxy, the corresponding repository can
be used through dependency injection.
