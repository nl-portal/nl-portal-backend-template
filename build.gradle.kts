buildscript {
    apply("gradle/environment.gradle.kts")
}

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.10"
    //IntelliJ IDEA
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.1"
    id("org.jetbrains.kotlin.plugin.spring") version "1.6.10"
}

apply("gradle/test.gradle.kts")


sourceSets {
    this.getByName("main"){
        this.java.srcDir("src/main/java")
        this.java.srcDir("src/main/kotlin")
    }
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "17"
}

group = "com.ritense.valtimo.portal"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    // Valtimo
    implementation("nl.nl-portal:case:0.1.0.RELEASE")
    implementation("nl.nl-portal:form:0.1.0.RELEASE")
    implementation("nl.nl-portal:graphql:0.1.0.RELEASE")
    implementation("nl.nl-portal:zaak:0.1.0.RELEASE")
    implementation("nl.nl-portal:core:0.1.0.RELEASE")
    implementation("nl.nl-portal:data:0.1.0.RELEASE")
    implementation("nl.nl-portal:form-flow:0.1.0.RELEASE")
    implementation("nl.nl-portal:haalcentraal:0.1.0.RELEASE")
    implementation("nl.nl-portal:klant:0.1.0.RELEASE")
    implementation("nl.nl-portal:messaging:0.1.0.RELEASE")
    implementation("nl.nl-portal:product:0.1.0.RELEASE")
    implementation("nl.nl-portal:task:0.1.0.RELEASE")
    implementation("nl.nl-portal:common-ground-authentication:0.1.0.RELEASE")
    implementation("nl.nl-portal:common-ground-authentication-test:0.1.0.RELEASE")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.5")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")


    // Postgres
    implementation("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}

apply(from = "gradle/deployment.gradle")
