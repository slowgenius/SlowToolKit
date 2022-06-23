spring:
  cloud:
    nacos:
      discovery:
        server-addr: 10.0.0.22:8848
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{dbIp}:{dbPort}/{dbName}?useUnicode=true&serverTimezone=Asia/Shanghai&useSSL=false&characterEncoding=utf8
    username: {dbUsername}
    password: {dbPassword}
  redis:
    host: 10.0.0.22
    port: 6379
    password: lylb@123456
    jedis:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 500
        min-idle: 0
    database: 6
