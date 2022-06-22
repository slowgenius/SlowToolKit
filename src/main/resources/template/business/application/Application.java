package {projectPackage}.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"{basePackage}"})
@EnableDiscoveryClient
@EnableScheduling
@EnableAsync
@EnableFeignClients(basePackages = "{projectPackage}.integration")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
