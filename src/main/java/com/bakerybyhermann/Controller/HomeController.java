package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

    @GetMapping("/")
    public String getIndex(){
        return "home/index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "home/login";
    }



}
