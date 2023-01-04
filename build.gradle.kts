plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "com.slowgenius"
version = "2.8.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.hutool:hutool-all:5.8.9")
    implementation("com.alibaba:fastjson:2.0.19")
    implementation("org.freemarker:freemarker:2.3.31")
    implementation("org.reflections:reflections:0.10.2")
}


intellij {
    //version.set("2021.2")
    localPath.set("/Applications/IntelliJ IDEA.app")
    type.set("IU")
    plugins.set(listOf("com.intellij.java", "com.intellij.database"))

}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
    patchPluginXml {
        sinceBuild.set("193")
        untilBuild.set("222.*")
    }

    publishPlugin {
        token.set(System.getenv("intellijPublishToken"))
    }
}
