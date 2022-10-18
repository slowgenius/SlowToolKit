plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "com.slowgenius"
version = "2.6.1"

repositories {
    mavenCentral()
//    maven("https://jetbrains.bintray.com/intellij-third-party-dependencies")
//    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
}

dependencies {
    implementation("cn.hutool:hutool-all:5.7.16")
    implementation("com.alibaba:fastjson:2.0.14")
    implementation("org.freemarker:freemarker:2.3.31")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin

intellij {
    //version.set("2021.2")
    localPath.set("/Applications/IntelliJ IDEA.app")
    type.set("IU") // Target IDE Platform
    plugins.set(listOf("com.intellij.java", "com.intellij.database"))

}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("193")
        untilBuild.set("222.*")
    }

    publishPlugin {
        token.set(System.getenv("intellijPublishToken"))
    }
}
