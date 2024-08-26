dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testFixturesImplementation(rootProject.libs.bundles.kotest)
    testFixturesImplementation(rootProject.libs.bundles.mockk)
    testFixturesImplementation("com.github.codemonstur:embedded-redis:1.0.0")
}
