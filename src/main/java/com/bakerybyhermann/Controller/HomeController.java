package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

    @Autowired
    CustomerService customerService;


    @GetMapping("/show-customer")
    public String getIndex(Model model){
        
        model.addAttribute("customersList",customerService.fetchAll());
        return "customer/show-customer";
    }

    @GetMapping("/")
    public String getIndex1(){
        return "home/index";
    }



}
