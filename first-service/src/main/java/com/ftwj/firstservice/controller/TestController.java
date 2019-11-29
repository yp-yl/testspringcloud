package com.ftwj.firstservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {
    @RequestMapping("add")
    public String add(){
        System.out.println("this is firstService");
        return "this isfirstService";
    }
}
