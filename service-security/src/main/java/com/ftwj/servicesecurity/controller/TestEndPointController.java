package com.ftwj.servicesecurity.controller;

import com.ftwj.servicesecurity.service.UserService;
import com.ftwj.servicesecurity.vo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class TestEndPointController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "/member")
    public Principal user(Principal member) {

        return member;
    }

    @GetMapping(value = "/member2")
    public String user1() {

        return "122";
    }
}
