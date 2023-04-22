dependencies {
    // QueryDSL
    compileOnly("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api:2.1.1")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
