package com.zyf.oms.controllers;

import com.zyf.oms.repositories.ADOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class ADOrderController {
    @Autowired
    ADOrderRepository adorderRepository;
}
