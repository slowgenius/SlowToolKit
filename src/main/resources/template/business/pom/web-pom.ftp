<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>${projectName}</artifactId>
        <groupId>mp.lylb</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>${projectName}-web</artifactId>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-integration</artifactId>
        </dependency>

        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-service</artifactId>
        </dependency>

        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-util</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-context</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--监控系统健康情况的工具-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--micrometer核心包, 按需引入, 使用Meter注解或手动埋点时需要-->
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-core</artifactId>
        </dependency>
        <!--桥接Prometheus-->
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
        </dependency>
        <!--micrometer获取JVM相关信息, 并展示在Grafana上-->
        <dependency>
            <groupId>io.github.mweirauch</groupId>
            <artifactId>micrometer-jvm-extras</artifactId>
        </dependency>

        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>mp-deregister-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>mp-log-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>mp-web-config</artifactId>
        </dependency>

        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>mp-druid-support</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.5.1</version>
                <configuration>
                    <fork>true</fork> <!-- 如果没有该配置，devtools不会生效 -->
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>