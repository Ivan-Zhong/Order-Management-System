package com.zyf.oms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/test")
public class testController {
    private String folderPath = "./src/main/resources/static/images/";

    @GetMapping("/helloOMS")
    public String helloOMS(){
        return "Hello OMS!";
    }

    @GetMapping("/testCreateFile")
    public void testCreateFile(){
        File file = new File(folderPath);
        file.mkdirs();
    }

    @GetMapping("/image/download/{fileName}")
    public void downloadImage(@PathVariable String fileName,
                              HttpServletResponse response){
        if (fileName.indexOf(".doc")>-1) response.setContentType("application/msword");
        if (fileName.indexOf(".docx")>-1) response.setContentType("application/msword");
        if (fileName.indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".csv")>-1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".ppt")>-1) response.setContentType("application/ppt");
        if (fileName.indexOf(".pdf")>-1) response.setContentType("application/pdf");
        if (fileName.indexOf(".zip")>-1) response.setContentType("application/zip");
        if (fileName.indexOf(".jpg")>-1) response.setContentType("image/jpeg");
        if (fileName.indexOf(".JPG")>-1) response.setContentType("image/jpeg");
        if (fileName.indexOf(".jpeg")>-1) response.setContentType("image/jpeg");
        if (fileName.indexOf(".png")>-1) response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; fileName=" +fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+fileName);
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
}
