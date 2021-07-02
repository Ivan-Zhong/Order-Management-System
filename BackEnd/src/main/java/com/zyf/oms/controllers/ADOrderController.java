package com.zyf.oms.controllers;

import com.zyf.oms.entities.ADOrder;
import com.zyf.oms.entities.Client;
import com.zyf.oms.entities.ReturnValue;
import com.zyf.oms.repositories.ADOrderRepository;
import com.zyf.oms.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
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

    @GetMapping("/design/read/all")
    public ReturnValue designReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("designer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus2("measured", "designed"));
        }
        return rv;
    }

    @PostMapping("/design/update/{id}")
    public ReturnValue designUpdateOne(@PathVariable("id") int id,
                                        @RequestParam("material") String material,
                                        @RequestParam("image") MultipartFile designImage,
                                        HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("designer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            // 设置material
            adorder.setMaterial(material);
            // 新增
            if(adorder.getStatus().equals("measured")){
                // 接收文件
                String imagename = new Date().getTime() + designImage.getOriginalFilename();
                try {
                    designImage.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\" + imagename));
                }catch(IOException e){
                    e.printStackTrace();
                }
                adorder.setImagename(imagename);
                adorder.setStatus("designed");
                adorderRepository.save(adorder);
            }
            // 更新
            else if(adorder.getStatus().equals("designed") && !designImage.isEmpty()){
                String originalImagename = adorder.getImagename();
                // 删除原始文件
                File originalImage = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\" + originalImagename);
                if(originalImage.exists()){
                    originalImage.delete();
                }
                // 接收新文件并更新数据库相应字段
                // 接收文件
                String imagename = new Date().getTime() + designImage.getOriginalFilename();
                try {
                    designImage.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\" + imagename));
                }catch(IOException e){
                    e.printStackTrace();
                }
                adorder.setImagename(imagename);
                adorderRepository.save(adorder);
            }
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/image/download/{imagename}")
    public void downloadImage(@PathVariable String imagename,
                              HttpServletResponse response){
        if (imagename.indexOf(".doc")>-1) response.setContentType("application/msword");
        if (imagename.indexOf(".docx")>-1) response.setContentType("application/msword");
        if (imagename.indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");
        if (imagename.indexOf(".csv")>-1) response.setContentType("application/vnd.ms-excel");
        if (imagename.indexOf(".ppt")>-1) response.setContentType("application/ppt");
        if (imagename.indexOf(".pdf")>-1) response.setContentType("application/pdf");
        if (imagename.indexOf(".zip")>-1) response.setContentType("application/zip");
        if (imagename.indexOf(".jpg")>-1) response.setContentType("image/jpeg");
        if (imagename.indexOf(".JPG")>-1) response.setContentType("image/jpeg");
        if (imagename.indexOf(".jpeg")>-1) response.setContentType("image/jpeg");
        if (imagename.indexOf(".png")>-1) response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; fileName=" +imagename);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\" + imagename);
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/price/read/all")
    public ReturnValue priceReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("pricer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus2("designed", "priced"));
        }
        return rv;
    }

    @PostMapping("/price/update/{id}")
    public ReturnValue priceUpdateOne(@PathVariable("id") int id,
                                        @RequestParam("price") int price,
                                        HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("pricer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setPrice(price);
            adorder.setStatus("priced");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/consider/read/all")
    public ReturnValue considerReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("clienter")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus3("priced", "accepted", "rejected"));
        }
        return rv;
    }

    @GetMapping("/consider/update/accept/{id}")
    public ReturnValue considerAcceptOne(@PathVariable("id") int id,
                                          HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("clienter")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setStatus("accepted");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/consider/update/reject/{id}")
    public ReturnValue considerRejectOne(@PathVariable("id") int id,
                                         HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("clienter")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setStatus("rejected");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/produce/read/all")
    public ReturnValue produceReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("factory")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus2("accepted", "produced"));
        }
        return rv;
    }

    @GetMapping("/produce/update/{id}")
    public ReturnValue produceUpdateOne(@PathVariable("id") int id,
                                         HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("factory")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setStatus("produced");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/install/read/all")
    public ReturnValue installReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("installer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus2("produced", "installed"));
        }
        return rv;
    }

    @GetMapping("/install/update/{id}")
    public ReturnValue installUpdateOne(@PathVariable("id") int id,
                                        HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("installer")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setStatus("installed");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/finish/read/all")
    public ReturnValue finishReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("finisher")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAllByStatus3("installed", "rejected", "finished"));
        }
        return rv;
    }

    @GetMapping("/finish/update/{id}")
    public ReturnValue finishUpdateOne(@PathVariable("id") int id,
                                        HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!(session.getAttribute("identity").equals("root"))
                        && !(session.getAttribute("identity").equals("finisher")))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            // 获得这个id的订单
            ADOrder adorder = adorderRepository.findById(id).get();
            adorder.setStatus("finished");
            adorderRepository.save(adorder);
            rv.setMessage("success");
            rv.setData(null);
        }
        return rv;
    }

    @GetMapping("/root/read/all")
    public ReturnValue rootReadAll(HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!session.getAttribute("identity").equals("root"))){
            rv.setMessage("failure");
            rv.setData(null);
        }
        else{
            rv.setMessage("success");
            rv.setData(adorderRepository.findAll());
        }
        return rv;
    }

    @GetMapping("/delete/{id}")
    public ReturnValue rootDeleteOne(@PathVariable("id") int id,
                                     HttpServletRequest request){
        ReturnValue rv = new ReturnValue();
        HttpSession session = request.getSession(false);
        if(session == null ||
                (!session.getAttribute("identity").equals("root"))){
            rv.setMessage("failure");
            rv.setData(null);
        }else{
            rv.setMessage("success");
            adorderRepository.deleteById(id);
            rv.setData(null);
        }
        return rv;
    }
}
