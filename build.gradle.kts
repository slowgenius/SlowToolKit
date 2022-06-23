plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "com.slowgenius"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/cn.hutool/hutool-all
    implementation("cn.hutool:hutool-all:5.7.16")
    // https://mvnrepository.com/artifact/com.alibaba/fastjson
    implementation("com.alibaba:fastjson:1.2.78")

    implementation("org.freemarker:freemarker:2.3.31")

}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    publishPlugin {
        token.set("amalformedtoken")
    }
}
