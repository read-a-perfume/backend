dependencies {
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:6.21.0")
    implementation("io.sentry:sentry-logback:6.22.0")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.1.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
