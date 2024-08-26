# Reactive Redis Extension

> **Spring Data Redis에서 사용되는 `RedisRepository`의 리액티브(Reactive) 확장**

## Description

Spring Data Reactive Redis에서는 기존의 `RedisRepository`의 리액티브 변형을 지원하지 않는다.
그래서 엔티티마다 반복적인 코드를 작성해주어야 하는 불편함이 있는데,
이를 자체적인 `ReactiveRedisRepository`를 구현함으로써 해결하고자 한다.

해당 확장 기능은 기존의 Spring Data처럼 `ReactiveRedisRepository`만 상속받으면 자동으로 Redis에 대한 조회, 저장, 삭제에 대한 기능을 구현체를 생성해준다.
또한 추가적으로 Reactor 뿐만 아니라 코루틴을 사용하는 환경에서도 해당 확장을 사용할 수 있도록 `CoroutineRedisRepository`를 제공한다.

## How to use

**1. 설정 클래스 정의**

```kotlin
@EnableReactiveRedisRepositories(basePackages = ["com.github"])
@Configuration
class ReactiveRedisConfiguration
```

`@EnableReactiveRedisRepositories`의 `basePackages`에 해당하는 패키지 하위에서만 `ReactiveRedisRepository`를 스캔하고 구현체를 Bean으로 등록한다.

코루틴의 경우 `@EnableCoroutineRedisRepository`을 통해 `@EnableReactiveRedisRepositories`와 같은 기능을 사용할 수 있다.

**2. 엔티티 및 레포지토리 정의**

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

엔티티에서 `@Key`에 해당하는 필드가 Redis에 저장시 사용될 Key가 된다.
실제로 저장되는 값은 엔티티에 대한 JSON(JavaScript Object Notation) 문자열이다.

**3. 레포지토리를 주입 받아 사용**

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
