plugins {
    id("java")
    id("application")
}

group = "de.abramov"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("de.abramov.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}