val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation(project(":perfume-core"))
    implementation(project(":perfume-support"))

    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    implementation("org.springframework.boot:spring-boot-starter-web:3.1.3")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.1.3")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.3")

    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("org.flywaydb:flyway-mysql:9.22.0")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.3")
    compileOnly("org.projectlombok:lombok:1.18.28")

    annotationProcessor("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.1.3")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:2.1.1")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    testImplementation("com.h2database:h2:2.2.222")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("io.rest-assured:rest-assured:5.3.1")
    testImplementation("org.springframework.security:spring-security-test:6.1.3")
}

tasks.jar {
    enabled = false
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    doFirst {
        delete("src/main/resources/static/docs")
    }
    inputs.dir(snippetsDir)
    configurations(asciidoctorExt.name)
    dependsOn(tasks.test)
    doLast {
        copy {
            from("build/docs/asciidoc")
            into("src/main/resources/static/docs")
        }
        copy {
            from("build/docs/asciidoc")
            into("build/resources/main/static/docs")
        }
    }
}

tasks.bootJar {
    dependsOn(tasks.asciidoctor)
}
