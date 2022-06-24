<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>mp.lylb</groupId>
        <artifactId>${projectName}</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <groupId>mp.lylb</groupId>
    <artifactId>${projectName}-web</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-biz</artifactId>
        </dependency>
        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-dal</artifactId>
        </dependency>
        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-facade</artifactId>
        </dependency>
        <dependency>
            <groupId>mp.lylb</groupId>
            <artifactId>${projectName}-integration</artifactId>
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
            <version>0.2.0</version>
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
            <artifactId>mp-token-parse-starter</artifactId>
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
