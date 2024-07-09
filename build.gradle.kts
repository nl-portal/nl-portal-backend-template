import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.net.URI

buildscript {
    apply("gradle/environment.gradle.kts")
}

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.jpa") version "2.0.0"
    // IntelliJ IDEA
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.0"

    // Linting
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

apply("gradle/test.gradle.kts")

sourceSets {
    this.getByName("main") {
        this.java.srcDir("src/main/java")
        this.java.srcDir("src/main/kotlin")
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjsr305=strict")
        freeCompilerArgs.add("-Xemit-jvm-type-annotations")
    }
}

group = "nl.nl-portal.backend"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
    maven(URI("https://oss.sonatype.org/content/repositories/releases"))
    maven(URI("https://s01.oss.sonatype.org/content/groups/staging/"))
    maven(URI("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

val backend_libraries_release_version = "1.4.9-SNAPSHOT"
val okHttp3 = "4.12.0"
val springSecurityOAuth = "2.5.2.RELEASE"
val kotlinLogging = "3.0.5"
val mockitoKotlin = "5.4.0"
val kotlinCoroutines = "1.8.1"
val backend_libraries_version =
    if (project.hasProperty("libraryVersion") && project.property("libraryVersion").toString().trim() != "") {
        project.property("libraryVersion")
    } else {
        backend_libraries_release_version
    }
println("Version of nl-portal-backend-libraries '${backend_libraries_version}' will be deployed")

dependencies {
    // NL Portal Library dependencies
    implementation("nl.nl-portal:case:$backend_libraries_version")
    implementation("nl.nl-portal:form:$backend_libraries_version")
    implementation("nl.nl-portal:graphql:$backend_libraries_version")

    // zgw
    implementation("nl.nl-portal:zaken-api:$backend_libraries_version")
    implementation("nl.nl-portal:documenten-api:$backend_libraries_version")
    implementation("nl.nl-portal:catalogi-api:$backend_libraries_version")
    implementation("nl.nl-portal:objectenapi:$backend_libraries_version")
    implementation("nl.nl-portal:taak:$backend_libraries_version")

    implementation("nl.nl-portal:core:$backend_libraries_version")
    implementation("nl.nl-portal:data:$backend_libraries_version")
    implementation("nl.nl-portal:haalcentraal-all:$backend_libraries_version")
    // Below haalcentraal dependencies are implicitly added via haalcentraal-all
    // implementation("nl.nl-portal:haalcentraal-brp:$backend_libraries_version")
    // implementation("nl.nl-portal:haalcentraal-hr:$backend_libraries_version")
    implementation("nl.nl-portal:klant:$backend_libraries_version")
    implementation("nl.nl-portal:klant-generiek:$backend_libraries_version")
    implementation("nl.nl-portal:klantcontactmomenten:$backend_libraries_version")
    implementation("nl.nl-portal:messaging:$backend_libraries_version")
    implementation("nl.nl-portal:product:$backend_libraries_version")
    implementation("nl.nl-portal:payment:$backend_libraries_version")
    implementation("nl.nl-portal:common-ground-authentication:$backend_libraries_version")
    implementation("nl.nl-portal:common-ground-authentication-test:$backend_libraries_version")

    // Spring dependencies
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.security.oauth:spring-security-oauth2:$springSecurityOAuth")

    // Kotlin logger dependency
    implementation("io.github.microutils:kotlin-logging:$kotlinLogging")

    // Postgres dependency
    implementation("org.postgresql:postgresql")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.squareup.okhttp3:okhttp:$okHttp3")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okHttp3")
    testImplementation("com.squareup.okhttp3:okhttp-tls:$okHttp3")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlin")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutines")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

apply(from = "gradle/deployment.gradle")