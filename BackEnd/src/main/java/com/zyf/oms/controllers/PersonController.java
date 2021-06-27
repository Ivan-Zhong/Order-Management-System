package com.zyf.oms.controllers;

import com.zyf.oms.entities.Person;
import com.zyf.oms.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/login")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password){
        Person person = personRepository.findBy

    }

}
