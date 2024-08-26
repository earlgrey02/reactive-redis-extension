dependencies {
    implementation(project(":redis-core"))
    implementation(project(":redis-reactor"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testFixturesImplementation(testFixtures(project(":redis-core")))
}
