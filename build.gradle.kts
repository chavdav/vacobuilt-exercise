import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.15"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("org.flywaydb.flyway") version "9.20.0"
}

group = "me.chavdav.vacobuilt"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.jetbrains.exposed:exposed-core:0.42.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.42.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.42.1")
    implementation("org.flywaydb:flyway-core:9.20.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/test"
    user = "postgres"
    password = "postgres"
    driver = "org.postgresql.Driver"
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:src/main/resources/db/migration/")
    cleanDisabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

springBoot {
    mainClass.value("me.chavdav.vacobuilt.AppKt")
}