package com.bakerybyhermann.Controller;
import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
/* Dette er en controller klasse,
 * som kommer til at håndterer http get/post requests.
 * Det der sker herinde, er at controlleren kalder på servicelaget(mellemlag),som kalder på repository.
 * Når der kaldes en metode fra service, så mappes den i model med modelAttribute,
 * som efterfølgende displayes via thymeleaf/html.
 */

@Controller
public class CustomerController {

    // Der skabes en ny instans af den klasse, som er annoteret med autowired. Det vil sige, man nu kan kalde metoder på objektet.
    @Autowired
    CustomerService customerService;

    // Her mappes en get http request til getCustomer metoden.
    // Det vil sige, på /show-customer end-pointet/stien, returneres customer/show-customer.
    @GetMapping("/show-customer")
    public String getCustomer(Model model){

        model.addAttribute("customersList",customerService.fetchAll());
        return "customer/show-customer";
    }

    @GetMapping("/new-customer")
    public String createCustomer(){
        return "customer/new-customer";
    }

    // Her mappes en post http request til createCustomer, så vi kan create en customer
    @PostMapping("/new-customer")
    public String createCustomer(@ModelAttribute Customer customer, @ModelAttribute Address address){
        customer.setAddress(address);
        customerService.addNew(customer, address);
        return "redirect:/show-customer";
    }

    @GetMapping("/customer/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") int customerId){
        customerService.delete(customerId);
        return "redirect:/show-customer";
    }

    @GetMapping("/view-customer/{customerId}")
    public String viewOne(@PathVariable("customerId") int id, Model model){
        model.addAttribute("oneCustomer", customerService.findById(id));
        return "customer/one-customer";
    }

    @GetMapping("/update-customer/{id}")
    public String updateCustomer(@PathVariable("id") int id, Model model){
        model.addAttribute("customer", customerService.findById(id));
        return "customer/update-customer";
    }

    @PostMapping("/update-customer")
    public String updateCustomer(@ModelAttribute Customer customer, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        customerService.updateById(customer.getPersonId(), customer);
        return "redirect:"+referer;
    }


}
