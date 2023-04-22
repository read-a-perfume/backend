dependencies {
    implementation(project(":perfume-core"))

    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.0.6")

    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("org.mockito:mockito-core:5.1.1")
}
