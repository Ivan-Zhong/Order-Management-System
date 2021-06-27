package com.zyf.oms.controllers;

import com.zyf.oms.entities.Person;
import com.zyf.oms.entities.ReturnValue;
import com.zyf.oms.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/login")
    public ReturnValue login(@RequestParam("name") String name,
                        @RequestParam("password") String password){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        List<Person> persons = personRepository.findAllByName(name);
        if(persons.size() == 0)
            rv.setMessage("failure");
        else{
            if(!persons.get(0).getPassword().equals(password))
                rv.setMessage("failure");
            else
                rv.setMessage("success");
        }
        return rv;
    }

}
