package com.ftwj.eurekafeign.fallback;

import com.ftwj.eurekafeign.service.TestService;
import org.springframework.stereotype.Component;

/**
 * 容错类
 */
@Component
public class TestServiceBack implements TestService {

    @Override
    public String add() {
        return null;
    }
}
