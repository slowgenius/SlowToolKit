server:
port: 8801
shutdown: graceful

spring:
profiles:
active: dev
application:
name: ${projectName}
cloud:
nacos:
discovery:
port: <#noparse>${server.port}</#noparse>
register-enabled: true
jackson:
default-property-inclusion: non_null

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

feign:
client:
config:
default:
connectTimeout: 60000
readTimeout: 60000
httpclient:
connection-timeout: 60000
connection-timer-repeat: 60000
enabled: true
max-connections: 200
max-connections-per-route: 50

# apollo
app:
id: {projectName}

# 日志格式
logging:
pattern:
console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] tid -> %X{trace.id} %logger{50} - %msg%n"
file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] tid -> %X{trace.id} %logger{50} - %msg%n"