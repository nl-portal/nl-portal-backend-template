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
    maven { url = uri("https://repo.ritense.com/repository/maven-snapshots/") }
    maven { url = uri("https://repo.ritense.com/repository/maven-releases/") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    // Valtimo
    implementation("com.ritense.valtimo.portal:case:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:form:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:graphql:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:zaak:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:core:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:data:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:form-flow:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:haalcentraal:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:klant:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:messaging:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:product:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:task:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:common-ground-authentication:wip-keycloak-user-authentication-11")
    implementation("com.ritense.valtimo.portal:common-ground-authentication-test:wip-keycloak-user-authentication-11")

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
