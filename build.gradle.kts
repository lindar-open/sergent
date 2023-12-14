import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.lindar"
version = "3.0.0"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    `maven-publish`
}

java {
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

fun RepositoryHandler.mavenRepo(type: String) {
    maven {
        url = uri(properties["url.$type"].toString())
        isAllowInsecureProtocol = true
        credentials {
            username = properties["username.$type"].toString()
            password = properties["password.$type"].toString()
        }
    }
}

repositories {
    mavenCentral()
    listOf("SNAPSHOT", "RELEASE").forEach { mavenRepo(it) }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")

    // JUnit Jupiter API and Engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // For Kotlin-specific testing features
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.10")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
}

publishing {
    publications {
        create("defaultJar", MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()
            from(components["kotlin"])
        }
    }
    repositories {
        val type = if (version.toString().endsWith("SNAPSHOT")) "SNAPSHOT" else "RELEASE"
        mavenRepo(type)
    }
}
