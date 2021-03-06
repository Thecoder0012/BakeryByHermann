package com.bakerybyhermann.Controller;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Baker;
import com.bakerybyhermann.Service.BakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BakerController {

    @Autowired
    BakerService bakerService;

    @GetMapping("/baker")
    public String getBaker(Model model) {
        model.addAttribute("bakersList", bakerService.fetchAll());
        return "baker/view-baker";
    }

    @GetMapping("/new-baker")
    public String createBaker() {
        return "baker/new-baker";
    }

    @PostMapping("/new-baker")
    public String createBaker(@ModelAttribute Baker baker, @ModelAttribute Address address) {
        baker.setAddress(address);
        bakerService.addNewBaker(baker, address);
        return "redirect:/baker";
    }


    @GetMapping("/update-baker/{bakerId}")
    public String updateBaker(@PathVariable("bakerId") int id, Model model) {
        Baker baker = bakerService.findById(id);
        model.addAttribute("baker", baker);
        return "baker/update-baker";
    }

    @PostMapping(value = "/update-baker")
    public String updateBaker(@ModelAttribute Baker baker, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        bakerService.updateById(baker);
        return "redirect:" + referer;
    }


    @GetMapping("/baker/{bakerId}")
    public String deleteBaker(@PathVariable("bakerId") int bakerId) {
        bakerService.deleteBaker(bakerId);
        return "redirect:/baker";
    }

    @GetMapping("/view-baker/{bakerId}")
    public String viewOne(@PathVariable("bakerId") int id, Model model) {
        model.addAttribute("oneBaker", bakerService.findById(id));
        return "baker/one-baker";
    }

}



