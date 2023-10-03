dependencies {
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.0")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
