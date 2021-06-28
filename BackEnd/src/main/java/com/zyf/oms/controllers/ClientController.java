package com.zyf.oms.controllers;

import com.zyf.oms.entities.Client;
import com.zyf.oms.entities.ReturnValue;
import com.zyf.oms.repositories.ADOrderRepository;
import com.zyf.oms.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ADOrderRepository adorderRepository;

    @PostMapping("/add")
    public ReturnValue addClient(@RequestParam("name") String name,
                                 @RequestParam("contactname") String contactname,
                                 @RequestParam("tel") String tel,
                                 @RequestParam("address") String address,
                                 HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        // 先检查是否有权限
        if(session == null ||
           (!session.getAttribute("identity").equals("root") && !session.getAttribute("identity").equals("handler"))){
            rv.setMessage("failure");
        }
        // 有权限时，先检查用户是否已存在
        else{
            List<Client> clients = clientRepository.getClientByName(name);
            if(clients.size() == 0){
                Client client = new Client();
                client.setName(name);
                client.setTel(tel);
                client.setContactname(contactname);
                client.setAddress(address);
                clientRepository.save(client);
                rv.setMessage("success");
            }
            else{
                rv.setMessage("failure");
            }
        }
        rv.setData(null);
        return rv;
    }

    @GetMapping("/read/all")
    public ReturnValue getAllClient(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("handler"))
                        && !(session.getAttribute("identity").equals("measurer"))
                        && !(session.getAttribute("identity").equals("pricer"))
                        && !(session.getAttribute("identity").equals("finisher")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(clientRepository.findAll());
        }
        return rv;
    }

    @GetMapping("/read/one/{id}")
    public ReturnValue getOneClient(@PathVariable int id, HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("handler"))
                        && !(session.getAttribute("identity").equals("measurer"))
                        && !(session.getAttribute("identity").equals("pricer"))
                        && !(session.getAttribute("identity").equals("finisher")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(clientRepository.findById(id));
        }
        return rv;
    }

    @PostMapping("/update/{id}")
    public ReturnValue updateOneClient(@PathVariable int id,
                                       @RequestParam("name") String name,
                                       @RequestParam("contactname") String contactname,
                                       @RequestParam("tel") String tel,
                                       @RequestParam("address") String address,
                                       HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        // 先检查是否有权限
        if(session == null ||
                (!session.getAttribute("identity").equals("root") && !session.getAttribute("identity").equals("handler"))){
            rv.setMessage("failure");
        }
        // 有权限时，先检查用户是否已存在
        else{
            List<Client> clients = clientRepository.getClientByName(name);
            if(clients.size() == 0){
                // 更新客户表信息
                clientRepository.updateClient(name, contactname, tel, address, id);
                // 更新订单表信息
                adorderRepository.updateClientname(name, id);
                rv.setMessage("success");
            }
            else{
                rv.setMessage("failure");
            }
        }
        rv.setData(null);
        return rv;
    }

    @GetMapping("/delete/{id}")
    public ReturnValue deleteClient(@PathVariable int id, HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null || !(session.getAttribute("identity").equals("root"))){
            rv.setMessage("failure");
        }
        else{
            rv.setMessage("success");
            clientRepository.deleteById(id);
        }
        rv.setData(null);
        return rv;
    }
}
