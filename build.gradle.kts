plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.java.library)
    alias(libs.plugins.java.fixture)
    alias(libs.plugins.dokka)
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

    configurations {
        matching { it.name.startsWith("dokka") }
            .configureEach {
                resolutionStrategy.eachDependency {
                    if (requested.group.startsWith("com.fasterxml.jackson")) {
                        useVersion("2.15.3")
                    }
                }
            }
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
        plugin(rootProject.libs.plugins.dokka)
    }

    configurations {
        matching { it.name.startsWith("dokka") }
            .configureEach {
                resolutionStrategy.eachDependency {
                    if (requested.group.startsWith("com.fasterxml.jackson")) {
                        useVersion("2.15.3")
                    }
                }
            }
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

tasks {
    dokkaHtmlMultiModule {
        moduleName.set("Reactive Redis Extension")
    }
}

private fun ObjectConfigurationAction.plugin(provider: Provider<PluginDependency>): ObjectConfigurationAction =
    plugin(provider.get().pluginId)

private fun DependencyHandler.test(dependencyNotation: Any) {
    testImplementation(dependencyNotation)
    testFixturesImplementation(dependencyNotation)
}

private fun initDokka() {
    configurations.matching { it.name.startsWith("dokka") }.configureEach {
        resolutionStrategy.eachDependency {
            if (requested.group.startsWith("com.fasterxml.jackson")) {
                useVersion("2.15.3")
            }
        }
    }
}
