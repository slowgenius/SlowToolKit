server:
  port: 8080

spring:
  cloud:
    nacos:
      discovery:
        port: <#noparse>${server.port}</#noparse>
        register-enabled: true
  profiles:
    active: dev
  application:
    name: {projectName}

  jackson:
    default-property-inclusion: non_null #service传输web,空值过滤

management:
  endpoints:
    web:
      exposure:
        include: prometheus, health
    shutdown:
      enable: false
  metrics:
    export:
      simple:
        enabled: false
    tags:
      application: <#noparse>${spring.application.name}</#noparse>

# 日志格式
logging:
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] tid -> %X{trace.id} %logger{50} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] tid -> %X{trace.id} %logger{50} - %msg%n"