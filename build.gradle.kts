import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0"
}

group = "io.perfume"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

allprojects {
    group = "io.perfume.backend"
    version = "0.0.1"
    repositories { mavenCentral() }
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.32")
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")

    dependencies {
        // spring boot
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // lombok
        compileOnly("org.projectlombok:lombok:1.18.26")
        annotationProcessor("org.projectlombok:lombok:1.18.26")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "19"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
