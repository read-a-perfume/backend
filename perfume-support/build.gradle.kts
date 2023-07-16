dependencies {
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:6.25.2")
    implementation("io.sentry:sentry-logback:6.25.2")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.1.1")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
