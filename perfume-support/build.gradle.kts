dependencies {
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:6.27.0")
    implementation("io.sentry:sentry-logback:6.26.0")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.1.2")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
