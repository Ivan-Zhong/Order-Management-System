package com.zyf.oms.controllers;

import com.zyf.oms.entities.Person;
import com.zyf.oms.entities.ReturnValue;
import com.zyf.oms.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/login")
    public ReturnValue login(@RequestParam("name") String name,
                             @RequestParam("password") String password,
                             HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        List<Person> persons = personRepository.findAllByName(name);
        if(persons.size() == 0)
            rv.setMessage("failure");
        else{
            if(!persons.get(0).getPassword().equals(password))
                rv.setMessage("failure");
            else{
                rv.setMessage("success");
                request.getSession().setAttribute("name", name);
                request.getSession().setAttribute("identity", persons.get(0).getIdentity());
            }
        }
        return rv;
    }

    @PostMapping("/add")
    public ReturnValue addPerson(@RequestParam("name") String name,
                                 @RequestParam("tel") String tel,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("identity") String identity){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        List<Person> persons = personRepository.findAllByName(name);
        if(persons.size() != 0){
            rv.setMessage("failure");
        }
        else{
            rv.setMessage("success");
            Person person = new Person();
            person.setName(name);
            person.setEmail(email);
            person.setTel(tel);
            person.setPassword(password);
            person.setIdentity(identity);
            personRepository.save(person);
        }
        return rv;
    }

    @GetMapping("/all")
    public ReturnValue getAllPerson(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        if(request.getSession(false) == null ||
                !request.getSession(false).getAttribute("identity").equals("root")){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(personRepository.findAll());
        }
        return rv;
    }

}
