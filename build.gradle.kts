plugins {
    id("java")
    id("application")
}

group = "de.abramov"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("de.abramov.Main")
}

dependencies {
    implementation("com.opencsv:opencsv:5.7.1")

    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}





tasks.test {
    useJUnitPlatform()
}