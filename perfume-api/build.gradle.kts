dependencies {
    implementation(project(":perfume-core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("org.mockito:mockito-core:5.1.1")
}
