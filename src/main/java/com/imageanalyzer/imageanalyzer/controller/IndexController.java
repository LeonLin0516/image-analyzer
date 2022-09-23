package com.imageanalyzer.imageanalyzer.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@CrossOrigin()
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/")
    public String getInputImage(@RequestParam("image") MultipartFile file) throws IOException {
        System.out.println("get input image");
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        byte[] bytes = file.getBytes();
        System.out.println(bytes.length);
        return "redirect:/";
    }

}
