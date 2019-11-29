package com.ftwj.eruekaribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient // 发现服务
public class EruekaRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EruekaRibbonApplication.class, args);
    }
    @Bean
    @LoadBalanced// 开启负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
