spring:
  cloud:
    nacos:
      discovery:
        server-addr: 10.0.0.22:8848
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
  jackson:
    default-property-inclusion: non_null #service传输web,空值过滤

apollo:
  meta: http://10.0.0.22:8080
  bootstrap:
    enabled: true
    eagerLoad:
      enabled: true
    namespaces: application,config