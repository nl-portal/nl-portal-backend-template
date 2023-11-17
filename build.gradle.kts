buildscript {
    apply("gradle/environment.gradle.kts")
}

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.20"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.20"
    //IntelliJ IDEA
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.10"
}

apply("gradle/test.gradle.kts")


sourceSets {
    this.getByName("main") {
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
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        mavenContent {snapshotsOnly()}
    }
    maven {
        url = uri("https://maven.pkg.github.com/nl-portal/nl-portal-backend-libraries")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GRP_USER")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GRP_TOKEN")
        }
    }
}

val backend_libraries_release_version = "1.2.5"
val backend_libraries_version = if(project.hasProperty("libraryVersion") && project.property("libraryVersion").toString().trim() != "" ) {
    project.property("libraryVersion")
} else {
    backend_libraries_release_version
}
println("Version of nl-portal-backend-libraries '${backend_libraries_version}' will be deployed")

dependencies {
    implementation("nl.nl-portal:case:$backend_libraries_version")
    implementation("nl.nl-portal:form:$backend_libraries_version")
    implementation("nl.nl-portal:graphql:$backend_libraries_version")

    //zgw
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
    if (!backend_libraries_version!!.equals("0.3.0.RELEASE")) {
        implementation("nl.nl-portal:klant-generiek:$backend_libraries_version")
        implementation("nl.nl-portal:klantcontactmomenten:$backend_libraries_version")
    }
    implementation("nl.nl-portal:messaging:$backend_libraries_version")
    implementation("nl.nl-portal:product:$backend_libraries_version")
    implementation("nl.nl-portal:common-ground-authentication:$backend_libraries_version")
    implementation("nl.nl-portal:common-ground-authentication-test:$backend_libraries_version")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.5")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")

    // Kotlin logger
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // Postgres
    implementation("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}

apply(from = "gradle/deployment.gradle")
