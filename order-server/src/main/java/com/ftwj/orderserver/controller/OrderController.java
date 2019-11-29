package com.ftwj.orderserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @RequestMapping("/add")
    public String addOrder(){
        return "this is addOrder";
    }
}
