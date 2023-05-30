val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation(project(":perfume-core"))
    implementation(project(":perfume-support"))

    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.1.0")
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.5")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.1.0")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.0")

    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.0.1")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("org.mockito:mockito-core:5.3.1")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:2.1.1")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

tasks.jar {
    enabled = false
}

val snippetsDir by extra { file("./build/generated-snippets") }

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    configurations(asciidoctorExt.name)
    dependsOn(tasks.test)
    doLast {
        copy {
            from("build/docs/asciidoc")
            into("build/resources/main/static")
        }
    }
}

tasks.build {
    dependsOn(tasks.asciidoctor)
}
