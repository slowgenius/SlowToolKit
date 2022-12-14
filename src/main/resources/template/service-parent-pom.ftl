<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>mp.lylb</groupId>
        <artifactId>lylb-dependencys</artifactId>
        <version>1.2-SNAPSHOT</version>
    </parent>
    <groupId>mp.lylb</groupId>
    <artifactId>${projectName}</artifactId>
    <version>1.2-SNAPSHOT</version>
    <packaging>pom</packaging>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>11</java.version>
        <project.version>1.0-SNAPSHOT</project.version>
        <mp-common-utils.version>1.0.3</mp-common-utils.version>
        <mp-log-starter.version>1.0.2</mp-log-starter.version>
        <mp-web-config.version>1.0.1</mp-web-config.version>
        <mp-deregister-starter.version>1.0.1</mp-deregister-starter.version>
        <mp-token-parse-starter.version>1.0.4</mp-token-parse-starter.version>
    </properties>
    <modules>
        <module>${projectName}-web</module>
        <module>app/${projectName}-biz</module>
        <module>app/core/${projectName}-service</module>
        <module>app/core/${projectName}-model</module>
        <module>app/common/${projectName}-dal</module>
        <module>app/common/${projectName}-util</module>
        <module>app/service/${projectName}-integration</module>
        <module>app/service/${projectName}-facade</module>
    </modules>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-web</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-biz</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-service</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-model</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-integration</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-facade</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-util</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-dal</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>mp-common-utils</artifactId>
                <version>1.1-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>mp-deregister-starter</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>mp-log-starter</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>mp-web-config</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>mp-token-parse-starter</artifactId>
                <version>1.1-SNAPSHOT</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
