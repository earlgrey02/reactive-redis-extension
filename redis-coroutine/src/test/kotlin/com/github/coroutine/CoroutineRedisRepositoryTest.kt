package com.github.coroutine

import com.github.core.fixture.KEY
import com.github.core.fixture.createEntity
import com.github.core.util.awaitFlush
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate

@CoroutineRedisTest
class CoroutineRedisRepositoryTest : BehaviorSpec() {
    @Autowired
    private lateinit var entityRepository: EntityRepository

    @Autowired
    private lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        redisTemplate.awaitFlush()
    }

    init {
        Given("엔티티가 존재하는 경우") {
            val entity = createEntity()
                .let { entityRepository.save(it) }

            When("해당 엔티티의 식별자를 통해 조회하면") {
                val result = entityRepository.findByKey(entity.key)

                Then("해당 엔티티가 조회된다.") {
                    result.shouldNotBeNull()
                    result shouldBeEqual entity
                }
            }

            When("해당 엔티티의 식별자와 같은 엔티티를 저장하면") {
                val newEntity = createEntity(content = "new_test")

                val saveResult = entityRepository.save(newEntity)
                val readResult = entityRepository.findByKey(entity.key)

                Then("기존 엔티티가 덮어 씌워진다.") {
                    saveResult shouldBeEqual newEntity
                    readResult.shouldNotBeNull()
                    readResult shouldBeEqual newEntity
                }
            }

            When("해당 엔티티의 식별자를 통해 삭제하면") {
                val deleteResult = entityRepository.deleteByKey(entity.key)
                val readResult = entityRepository.findByKey(entity.key)

                Then("해당 엔티티가 삭제된다.") {
                    deleteResult.shouldBeTrue()
                    readResult.shouldBeNull()
                }
            }
        }

        Given("엔티티가 존재하지 않는 경우") {
            When("존재하지 않는 식별자를 통해 조회하면") {
                val result = entityRepository.findByKey(KEY)

                Then("아무것도 조회되지 않는다.") {
                    result.shouldBeNull()
                }
            }

            When("존재하지 않는 식별자를 통해 삭제하면") {
                val result = entityRepository.deleteByKey(KEY)

                Then("아무것도 삭제되지 않는다.") {
                    result.shouldBeFalse()
                }
            }
        }

    }
}
