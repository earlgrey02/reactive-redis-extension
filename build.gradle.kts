plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.java.library)
    alias(libs.plugins.java.fixture)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

allprojects {
    group = "com.github"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin)
        plugin(rootProject.libs.plugins.spring.boot)
        plugin(rootProject.libs.plugins.spring.dependency.management)
        plugin(rootProject.libs.plugins.spring.kotlin)
        plugin(rootProject.libs.plugins.java.library)
        plugin(rootProject.libs.plugins.java.fixture)
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        test("org.springframework.boot:spring-boot-starter-test")
        test("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        test("io.projectreactor:reactor-test")
        test(rootProject.libs.bundles.kotest)
        test(rootProject.libs.bundles.mockk)
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }

        jar {
            enabled = true
        }

        bootJar {
            enabled = false
        }

        test {
            useJUnitPlatform()
        }
    }
}

private fun ObjectConfigurationAction.plugin(provider: Provider<PluginDependency>): ObjectConfigurationAction =
    plugin(provider.get().pluginId)

private fun DependencyHandler.test(dependencyNotation: Any) {
    testImplementation(dependencyNotation)
    testFixturesImplementation(dependencyNotation)
}
