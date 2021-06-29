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
                request.getSession().setAttribute("id", persons.get(0).getId());
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

    @GetMapping("/read/own")
    public ReturnValue getMyOwnInfo(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        if(request.getSession(false) == null){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(personRepository.findById((int)request.getSession(false).getAttribute("id")));
        }
        return rv;
    }

    @GetMapping("/read/all")
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

    @GetMapping("/read/others/{id}")
    public ReturnValue getAllPerson(@PathVariable int id,
                                    HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        if(request.getSession(false) == null ||
                !request.getSession(false).getAttribute("identity").equals("root")){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(personRepository.findById(id));
        }
        return rv;
    }

    @PostMapping("/update/own")
    public ReturnValue updateOwnInfo(@RequestParam("name") String newName,
                                     @RequestParam("tel") String tel,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        Person person = personRepository.getById((int)request.getSession(false).getAttribute("id"));
        List<Person> persons = personRepository.findAllByName(newName);
        if(persons.size() != 0 && !newName.equals(person.getName())){
            rv.setMessage("failure");
        }
        else{
            // 取得这个人的信息并更新
            person.setName(newName);
            person.setTel(tel);
            person.setEmail(email);
            person.setPassword(password);
            personRepository.save(person);
            request.getSession(false).setAttribute("name", newName);
            rv.setMessage("success");
        }
        return rv;
    }

    @PostMapping("/update/others/{id}")
    public ReturnValue updateOthersInfo(@PathVariable("id") int id,
                                     @RequestParam("name") String newName,
                                     @RequestParam("tel") String tel,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("identity") String identity,
                                     HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        Person person = personRepository.getById(id);
        List<Person> persons = personRepository.findAllByName(newName);
        if(persons.size() != 0 && !newName.equals(person.getName())){
            rv.setMessage("failure");
        }
        else{
            // 取得这个人的信息并更新
            person.setName(newName);
            person.setTel(tel);
            person.setEmail(email);
            person.setPassword(password);
            person.setIdentity(identity);
            personRepository.save(person);
            rv.setMessage("success");
            if(id == (int)request.getSession(false).getAttribute("id")){
                request.getSession(false).setAttribute("name", newName);
                request.getSession(false).setAttribute("identity", identity);
            }
        }
        return rv;
    }

    @GetMapping("/delete/{id}")
    public ReturnValue deletePerson(@PathVariable("id") int id,
                                    HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        if(request.getSession(false) == null ||
                !request.getSession(false).getAttribute("identity").equals("root") ||
                (int)request.getSession(false).getAttribute("id") == id){
            rv.setMessage("failure");
        }
        else{
            rv.setMessage("success");
            personRepository.deleteById(id);
        }
        return rv;
    }

    @GetMapping("/logout")
    public ReturnValue logout(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        rv.setData(null);
        if(request.getSession(false) == null)
            rv.setMessage("failure");
        else{
            rv.setMessage("success");
            request.getSession(false).invalidate();
        }
        return rv;
    }

}
