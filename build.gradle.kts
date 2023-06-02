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

    //Docker
    id("com.avast.gradle.docker-compose") version "0.16.11"
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
    implementation("nl.nl-portal:case:1.0.8.RELEASE")
    implementation("nl.nl-portal:form:1.0.8.RELEASE")
    implementation("nl.nl-portal:graphql:1.0.8.RELEASE")
    implementation("nl.nl-portal:zaak:1.0.8.RELEASE")
    implementation("nl.nl-portal:core:1.0.8.RELEASE")
    implementation("nl.nl-portal:data:1.0.8.RELEASE")
    implementation("nl.nl-portal:form-flow:1.0.8.RELEASE")
    implementation("nl.nl-portal:haalcentraal:1.0.8.RELEASE")
    implementation("nl.nl-portal:klant:1.0.8.RELEASE")
    implementation("nl.nl-portal:messaging:1.0.8.RELEASE")
    implementation("nl.nl-portal:product:1.0.8.RELEASE")
    implementation("nl.nl-portal:task:1.0.8.RELEASE")
    implementation("nl.nl-portal:common-ground-authentication:1.0.8.RELEASE")
    implementation("nl.nl-portal:common-ground-authentication-test:1.0.8.RELEASE")

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

dockerCompose {
    setProjectName(name) // uses projectRoot.name as the container group name

    stopContainers.set(true) // doesn't call `docker-compose down` if set to false; default is true
    removeContainers.set(false) // containers are retained upon composeDown for persistent storage
}

tasks.bootRun {
    dependsOn(tasks.composeUp) // bootrun starts a composeUp subtask
}

apply(from = "gradle/deployment.gradle")

 gradle.taskGraph.afterTask {
    if (this.name == "bootRun" && state.failure != null) { // gracefully stop the containers should bootRun task fail
        logger.log(LogLevel.WARN, "Stopping docker containers gracefully.")
        tasks.composeDown.get().down()
    }
 }
