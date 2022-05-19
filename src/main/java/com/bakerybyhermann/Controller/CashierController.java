package com.bakerybyhermann.Controller;


import com.bakerybyhermann.Service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CashierController {



    @Autowired
    CashierService cashierService;


    @GetMapping("/cashier")
    public String getCashier(Model model){
        model.addAttribute("cashierEmployee",cashierService.fetchAll());
        return "cashier/view-cashier";
    }

    @GetMapping("/cashier/{cashierId}")
    public String deleteCashier(@PathVariable("cashierId") int cashierId){
        cashierService.delete(cashierId);
        return "redirect:/";
    }

}
