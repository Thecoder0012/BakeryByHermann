package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    CustomerService customerService;


    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("customersList",customerService.fetchAll());
        for (int i = 0; i < customerService.fetchAll().size(); i++) {
            System.out.println(customerService.fetchAll().get(i).getEmail());
            System.out.println(customerService.fetchAll().get(i).getFirstName());
            System.out.println(customerService.fetchAll().get(i).getLastName());
            System.out.println(customerService.fetchAll().get(i).getAddress());
            System.out.println(customerService.fetchAll().get(i).getZipCode());
            System.out.println(customerService.fetchAll().get(i).getCity());
            System.out.println(customerService.fetchAll().get(i).getZipCode());
            System.out.println(customerService.fetchAll().get(i).getPhoneNumber());
            System.out.println(customerService.fetchAll().get(i).getEmail());



        }
        return "home/index";
    }

    @GetMapping("/new-customer")
    public String createCustomer(){
        return "home/new-customer";
    }

    @PostMapping("/new-customer")
    public String createCustomer(@ModelAttribute Customer customer){
        customerService.addNew(customer);
        return "redirect:/";
    }



}
