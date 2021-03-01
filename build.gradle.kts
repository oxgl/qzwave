import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.4.31"
val coroutinesVersion = "1.4.2"
val junitVersion = "5.7.1"
val kotlinLoggingVersion = "1.6.24"
val log4jApiKotlinVersion = "1.0.0"
val log4jVersion = "2.14.0"

plugins {
    kotlin("jvm") version "1.4.31"
}

group = "com.oxyggen.qzwave"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("com.neuronrobotics:nrjavaserial:5.2.1")
    implementation("com.fazecast:jSerialComm:[2.0.0,3.0.0)")
    implementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jApiKotlinVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}