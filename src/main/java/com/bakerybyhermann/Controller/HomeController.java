package com.bakerybyhermann.Controller;

//import com.bakerybyhermann.Service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
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

//    @GetMapping("/login")
//    public String authenticated(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
//            return "home/login";
//
//        }
//        return "redirect:/";
//    }

}
