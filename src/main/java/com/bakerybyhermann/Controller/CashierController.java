package com.bakerybyhermann.Controller;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Cashier;
import com.bakerybyhermann.Model.Driver;
import com.bakerybyhermann.Service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CashierController {

    @Autowired
    CashierService cashierService;


    @GetMapping("/cashier")
    public String getCashier(Model model){
        List<Cashier> cashierList = cashierService.fetchAll();
//        for (int i = 0; i<cashierList.size(); i++){
//            if (cashierList.get(i).isGender()){
//                cashier.setGender(Boolean.parseBoolean("Mand"));
//            }else{
//                cashier.setGender(Boolean.parseBoolean("Kvinde"));
//            }
//        }

        model.addAttribute("cashierEmployee",cashierList);
        return "cashier/view-cashier";
    }

    @GetMapping("/cashier/{cashierId}")
    public String deleteCashier(@PathVariable("cashierId") int cashierId){
        cashierService.delete(cashierId);
        return "redirect:/cashier";
    }

    // create get
    @GetMapping("/new-cashier")
    public String createCashier(){
        return "cashier/new-cashier";
    }

    // create post
    @PostMapping("/new-cashier")
    public String createCashier(@ModelAttribute Cashier cashier, @ModelAttribute Address address){
        cashier.setAddress(address);
        cashierService.addNew(cashier, address);
        return "redirect:/cashier";
    }

    // update get
    @GetMapping("/cashier-update/{cashierId}")
    public String editCashier(@PathVariable("cashierId") int cashierId,Model model){
        Cashier cashier = cashierService.findById(cashierId);
        model.addAttribute("woman", false);
        model.addAttribute("cashier",cashier);
        return "cashier/update-cashier";
    }

//     update get
    @PostMapping(value = "/update-cashier")
    public String updateDriver(@ModelAttribute Cashier cashier, HttpServletRequest request) {
    String referer = request.getHeader("Referer");
    cashierService.updateById(cashier);
    return "redirect:" + referer;
}

    @GetMapping("/view-cashier/{cashierId}")
    public String viewOne(@PathVariable("cashierId") int id, Model model){
        model.addAttribute("oneCashier", cashierService.findById(id));
        return "cashier/one-cashier";
    }
}
