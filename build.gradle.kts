plugins {
    kotlin("jvm") version "1.7.10"
    java
}

group = "de.thelooter"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.tuxcraft.eu/repository/maven-public/")
    }
}

java.sourceCompatibility = JavaVersion.VERSION_16
java.targetCompatibility = JavaVersion.VERSION_16



dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("eu.tuxcraft:DatabaseProvider:1.0.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

