import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("checkstyle")
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id("org.graalvm.buildtools.native") version "0.9.28"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.20"
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

// common dependencies
subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.asciidoctor.jvm.convert")
    apply(plugin = "checkstyle")

    dependencies {
        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.10")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")

        // spring boot
        implementation("org.springframework.boot:spring-boot-starter:3.1.5")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.5")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
        testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
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

    tasks.checkstyleMain {
        configFile = rootProject.file("config/checkstyle/google-checkstyle.xml")
        ignoreFailures = true
        source = fileTree("src/main/java")
        include("**/*.java")
    }

    tasks.checkstyleTest {
        configFile = rootProject.file("config/checkstyle/google-checkstyle.xml")
        ignoreFailures = true
        source = fileTree("src/test/java")
        include("**/*.java")
    }
}
