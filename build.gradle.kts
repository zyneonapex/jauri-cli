plugins {
    java
    id("org.graalvm.buildtools.native") version "0.10.3"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.zyneonstudios.apex"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        vendor.set(JvmVendorSpec.matching("GraalVM"))
    }
}

repositories {
    mavenCentral()
    maven("https://maven.nrfy.net/snapshots") { name = "nerofySnapshots" }
    maven("https://maven.nrfy.net/releases") { name = "nerofyReleases" }
}

dependencies {
    implementation("tools.jackson.core:jackson-databind:3.2.3")
    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor("info.picocli:picocli-codegen:4.7.7")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes("Main-Class" to "com.zyneonstudios.apex.jauri.cli.Main")
    }
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("jauri")
            mainClass.set("com.zyneonstudios.apex.jauri.cli.Main")
            fallback.set(false)
            buildArgs.add("--enable-native-access=ALL-UNNAMED")
            buildArgs.add("-J-Dfile.encoding=UTF-8")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
