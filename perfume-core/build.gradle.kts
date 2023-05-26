dependencies {
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
