package com.zyf.oms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/helloOMS")
    public String helloOMS(){
        return "Hello OMS!";
    }
}
