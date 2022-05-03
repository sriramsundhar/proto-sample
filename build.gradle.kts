import com.google.protobuf.gradle.*
import net.researchgate.release.GitAdapter


plugins {
    java
    id("com.google.protobuf") version "0.8.16"
    id("net.researchgate.release") version "2.8.1"
    `java-library`
    `maven-publish`
}

version = "$version"
group = "$group"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven {
        url = uri("https://repo1.maven.org/maven2")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.15.3")
    implementation("io.grpc:grpc-stub:1.30.0")
    implementation("io.grpc:grpc-protobuf:1.30.0")
    implementation("io.grpc:grpc-netty:1.30.0")

    testImplementation("junit:junit:4.13")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.15.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.30.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
release {
    failOnSnapshotDependencies = true
    with(propertyMissing("git") as GitAdapter.GitConfig) {
        requireBranch = "main"
    }
    preTagCommitMessage = "Gradle Release Plugin - pre tag commit: "
    tagCommitMessage = "Gradle Release Plugin - creating tag: "
    newVersionCommitMessage = "Gradle Release Plugin - new version commit: "
}
tasks {
    "afterReleaseBuild" {
        dependsOn(getTasksByName("publish", true))
    }
}
publishing {
    publications {
        repositories {
            maven {
                isAllowInsecureProtocol = true
                url =
                        System.getenv("GITHUB_REPOSITORY")?.toString()?.let { uri("https://maven.pkg.github.com/".plus(it)) }
                                ?: uri(findProperty("artifactory").toString())
                credentials {
                    username = System.getenv("ARTIFACTORY_USERNAME")?.toString()
                            ?: findProperty("artifactoryUsername").toString()
                    password = System.getenv("ARTIFACTORY_PASSCODE")?.toString()
                            ?: findProperty("artifactoryPassword").toString()
                }
            }
        }
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}