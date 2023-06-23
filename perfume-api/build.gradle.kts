val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation(project(":perfume-core"))
    implementation(project(":perfume-support"))

    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    implementation("org.springframework.boot:spring-boot-starter-web:3.1.1")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.1")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.1")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.1.1")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.1")
    compileOnly("org.projectlombok:lombok:1.18.28")

    annotationProcessor("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.1.1")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:2.1.1")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("io.rest-assured:rest-assured:5.3.1")
}

tasks.jar {
    enabled = false
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    configurations(asciidoctorExt.name)
    dependsOn(tasks.test)
}

tasks.build {
    dependsOn(tasks.asciidoctor)
    copy {
        from("build/docs/asciidoc")
        into("src/main/resources/static/docs")
    }
}
