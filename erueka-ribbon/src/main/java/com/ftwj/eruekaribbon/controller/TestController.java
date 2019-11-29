package com.ftwj.eruekaribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/add")
    public  String add(){
        return restTemplate.getForEntity("http://FIRSTSERVICE/add",String.class).getBody();
    }
}
