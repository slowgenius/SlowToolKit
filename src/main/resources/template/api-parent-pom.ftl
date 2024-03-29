<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>mp.lylb</groupId>
        <artifactId>lylb-dependencys</artifactId>
        <version>1.2-SNAPSHOT</version>
    </parent>

    <groupId>mp.lylb</groupId>
    <artifactId>${projectName}</artifactId>
    <version>1.0-SNAPSHOT</version>
    <modelVersion>4.0.0</modelVersion>
    <packaging>pom</packaging>


    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.version>1.0-SNAPSHOT</project.version>
        <mp-common-utils.version>1.0.3</mp-common-utils.version>
        <mp-log-starter.version>1.0.2</mp-log-starter.version>
        <mp-web-config.version>1.0.1</mp-web-config.version>
        <mp-deregister-starter.version>1.0.1</mp-deregister-starter.version>
        <mp-token-parse-starter.version>1.0.4</mp-token-parse-starter.version>
    </properties>
    <modules>
        <module>${projectName}-web</module>
        <module>app/common/${projectName}-util</module>
        <module>app/service/${projectName}-service</module>
        <module>app/service/${projectName}-integration</module>
    </modules>


    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-service</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>

            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-integration</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-util</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>
            <dependency>
                <groupId>mp.lylb</groupId>
                <artifactId>${projectName}-web</artifactId>
                <version><#noparse>${project.version}</#noparse></version>
            </dependency>

            <!--micrometer获取JVM相关信息, 并展示在Grafana上-->
            <dependency>
                <groupId>io.github.mweirauch</groupId>
                <artifactId>micrometer-jvm-extras</artifactId>
                <version>0.2.0</version>
            </dependency>

        </dependencies>
    </dependencyManagement>
</project>