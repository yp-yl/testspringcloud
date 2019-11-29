package com.ftwj.eurekafeign.service;

import com.ftwj.eurekafeign.fallback.TestServiceBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "firstService",fallback = TestServiceBack.class)
public interface TestService {
    @RequestMapping("/test/add")
    String add();
}
