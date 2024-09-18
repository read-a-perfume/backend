import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id("org.graalvm.buildtools.native") version "0.9.28"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("com.diffplug.spotless") version "6.25.0"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.23"
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
    apply(plugin = "com.diffplug.spotless")

    dependencies {
        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")

        // spring boot
        implementation("org.springframework.boot:spring-boot-starter:3.2.4")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
        testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    }

    tasks.test {
        testLogging {
            events("failed")
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-java-parameters")
            jvmTarget = "19"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    spotless {
        java {
            targetExclude("**/build/generated/**")
            removeUnusedImports()
            trimTrailingWhitespace()
            indentWithTabs()
            endWithNewline()
            importOrder("java", "javax", "org", "com")
            googleJavaFormat()
        }
        format("misc") {
            target("**/*.gradle", "**/*.md", "**/.gitignore")
            trimTrailingWhitespace()
            indentWithTabs()
            endWithNewline()
        }
        kotlin {
            target("**/*.kt")
            ktlint()
        }
    }
}
