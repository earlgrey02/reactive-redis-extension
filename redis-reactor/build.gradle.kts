dependencies {
    implementation(project(":redis-core"))
    testImplementation("io.projectreactor:reactor-test")
    testFixturesImplementation(testFixtures(project(":redis-core")))
    testFixturesImplementation("io.projectreactor:reactor-test")
}
