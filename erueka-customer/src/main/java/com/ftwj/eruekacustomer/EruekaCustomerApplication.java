package com.ftwj.eruekacustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ftwj"})
public class EruekaCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EruekaCustomerApplication.class, args);
    }

}
