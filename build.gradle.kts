import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id("org.graalvm.buildtools.native") version "0.9.21"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
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

// common dependencies
subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.asciidoctor.jvm.convert")

    dependencies {
        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

        // spring boot
        implementation("org.springframework.boot:spring-boot-starter:3.0.6")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
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

    val asciidoctorExt: Configuration by configurations.creating
    dependencies {
        asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    }

    val snippetsDir by extra { file("build/generated-snippets") }
    tasks {
        test {
            outputs.dir(snippetsDir)
        }

        asciidoctor {
            inputs.dir(snippetsDir)
            configurations(asciidoctorExt.name)
            dependsOn(test)
            doLast {
                copy {
                    from("build/docs/asciidoc")
                    into("src/main/resources/static/docs")
                }
            }
        }

        build {
            dependsOn(asciidoctor)
        }
    }
}
