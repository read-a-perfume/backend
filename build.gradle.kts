import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id("org.graalvm.buildtools.native") version "0.9.21"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.20"
    kotlin("plugin.jpa") version "1.8.21"
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

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")

    dependencies {
        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20-RC")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")

        // spring boot
        implementation("org.springframework.boot:spring-boot-starter:3.0.6")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.4")
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
