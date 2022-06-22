<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>mp.lylb</groupId>
        <artifactId>${projectName}</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../../../pom.xml</relativePath>

    </parent>
    <groupId>mp.lylb</groupId>
    <artifactId>${projectName}-facade</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>${projectName}-facade</name>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>mp-common-utils</artifactId>
        </dependency>

    </dependencies>
</project>
