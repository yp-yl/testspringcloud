package com.ftwj.eruekacustomer.controller;

import com.ftwj.eurekafeign.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    /**
     *
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return testService.add();
    }


}
