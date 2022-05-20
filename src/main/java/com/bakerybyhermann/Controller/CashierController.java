package com.bakerybyhermann.Controller;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Cashier;
import com.bakerybyhermann.Service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "redirect:/cashier";
    }

    // create get
    @GetMapping("/cashier/new")
    public String createCashier(){
        return "cashier/new-cashier";
    }

    // create post
    @PostMapping("/cashier/new")
    public String createCashier(@ModelAttribute Cashier cashier, @ModelAttribute Address address){
        cashier.setAddress(address);
        cashierService.addNew(cashier, address);
        return "redirect:/cashier";
    }

    // update get
    @GetMapping("/cashier-update/{cashierId}")
    public String editCashier(@PathVariable("cashierId") int cashierId,Model model){
        Cashier cashier = cashierService.findById(cashierId);
        model.addAttribute("cashier",cashier);
        return "cashier/update-cashier";
    }

//     update get
    @PostMapping("/cashier-update")
    public String editCashier(@ModelAttribute Cashier cashier){
        cashierService.updateById(cashier.getCashierId(),cashier);
        return "redirect:/cashier";
    }
}
