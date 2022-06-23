package ${basePackage}.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"mp.lylb"})
@EnableScheduling
@EnableAsync
@MapperScan({"${basePackage}.dal.*.mapper"})
@EnableFeignClients(basePackages = "${basePackage}.integration")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
