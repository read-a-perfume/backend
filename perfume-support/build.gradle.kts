dependencies {
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:7.1.0")
    implementation("io.sentry:sentry-logback:6.32.0")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.2.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
