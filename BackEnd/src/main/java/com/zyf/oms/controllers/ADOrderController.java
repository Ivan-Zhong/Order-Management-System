package com.zyf.oms.controllers;

import com.zyf.oms.entities.ADOrder;
import com.zyf.oms.entities.Client;
import com.zyf.oms.entities.ReturnValue;
import com.zyf.oms.repositories.ADOrderRepository;
import com.zyf.oms.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class ADOrderController {
    @Autowired
    ADOrderRepository adorderRepository;

    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/create/add")
    public ReturnValue createAdd(@RequestParam("clientname") String clientname,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!session.getAttribute("identity").equals("root") && !session.getAttribute("identity").equals("handler"))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            List<Client> clients = clientRepository.getClientByName(clientname);
            if(clients.size() == 0){
                rv.setMessage("failure");
                rv.setData(null);
            }else{
                ADOrder adOrder = new ADOrder();
                adOrder.setClientname(clientname);
                adOrder.setDescription(description);
                adOrder.setTitle(title);
                adOrder.setClientid(clients.get(0).getId());
                adOrder.setStatus("created");
                adorderRepository.save(adOrder);
                rv.setMessage("success");
                rv.setData(null);
            }
        }
        return rv;
    }

    @GetMapping("/create/read/all")
    public ReturnValue createReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("handler")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus("created"));
        }
        return rv;
    }

    @GetMapping("/read/one/{id}")
    public ReturnValue createReadOne(@PathVariable("id") int id,
                                     HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findById(id));
        }
        return rv;
    }

    @PostMapping("/create/update/{id}")
    public ReturnValue createUpdateOne(@PathVariable("id") int id,
                                 @RequestParam("clientname") String clientname,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("handler")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            List<Client> clients = clientRepository.getClientByName(clientname);
            if(clients.size() == 0){
                // 客户不存在，失败
                rv.setMessage("failure");
                rv.setData(null);
            }
            else{
                // 获得这个id的订单
                ADOrder adorder = adorderRepository.findById(id).get();
                adorder.setClientname(clientname);
                adorder.setDescription(description);
                adorder.setTitle(title);
                adorderRepository.save(adorder);
                rv.setMessage("success");
                rv.setData(null);
            }
        }
        return rv;
    }

    @GetMapping("/getStatus/{id}")
    public ReturnValue getStatus(@PathVariable("id") int id){
        ReturnValue rv = new ReturnValue();
        rv.setMessage("success");
        rv.setData(adorderRepository.findById(id).get().getStatus());
        return rv;
    }

    @GetMapping("/measure/read/all")
    public ReturnValue measureReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("measurer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus2("created", "measured"));
        }
        return rv;
    }

    @PostMapping("/measure/update/{id}")
    public ReturnValue measureUpdateOne(@PathVariable("id") int id,
                                 @RequestParam("length") int length,
                                 @RequestParam("width") int width,
                                 @RequestParam("height") int height,
                                 @RequestParam("number") int number,
                                 HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("measurer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setHeight(height);
            adorder.setLength(length);
            adorder.setNumber(number);
            adorder.setWidth(width);
            adorder.setStatus("measured");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

}
