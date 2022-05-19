package com.bakerybyhermann.Controller;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Driver;
import com.bakerybyhermann.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DriverController {

    @Autowired
    DriverService driverService;

    @GetMapping("/driver")
    public String getDriver(Model model){
        model.addAttribute("driversList",driverService.fetchAll());
        return "driver/index";
    }

    @GetMapping("/new-driver")
    public String createDriver(){
        return "driver/new-driver";
    }

    @PostMapping("/new-driver")
    public String createDriver(@ModelAttribute Driver driver, @ModelAttribute Address address){
        driver.setAddress(address);
        driverService.addNewDriver(driver, address);
        return "redirect:/";
    }

    @GetMapping("/update-driver/{id}")
    public String updateDriver(@PathVariable("id") int id, Model model){
        Driver driver = driverService.findById(id);
        model.addAttribute("driver", driver);
        return "driver/update-driver";
    }

    @PostMapping("/update-driver")
    public String updateCustomer(@ModelAttribute Driver driver){
        driverService.updateById(driver.getPersonId(), driver);
        return "redirect:/";
    }

    @GetMapping("/driver/{driverId}")
    public String deleteCustomer(@PathVariable("driverId") int driverId){
        driverService.deleteDriver(driverId);
        return "redirect:/";
    }

    @GetMapping("/driver/{driverId}")
    public String deleteCashier(@PathVariable("driverId") int driverId){
        driverService.deleteDriver(driverId);
        return "redirect:/";
    }

}
