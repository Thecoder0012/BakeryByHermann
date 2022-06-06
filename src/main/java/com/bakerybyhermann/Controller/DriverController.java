package com.bakerybyhermann.Controller;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Driver;
import com.bakerybyhermann.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DriverController {

    @Autowired
    DriverService driverService;

    @GetMapping("/driver")
    public String getDriver(Model model) {
        model.addAttribute("driversList", driverService.fetchAll());
        return "driver/view-driver";
    }

    @GetMapping("/new-driver")
    public String createDriver() {
        return "driver/new-driver";
    }

    @PostMapping("/new-driver")
    public String createDriver(@ModelAttribute Driver driver, @ModelAttribute Address address) {
        driver.setAddress(address);
        driverService.addNewDriver(driver, address);
        return "redirect:/driver";
    }

    @GetMapping("/update-driver/{driverId}")
    public String updateDriver(@PathVariable("driverId") int id, Model model) {
        Driver driver = driverService.findById(id);
        model.addAttribute("driver", driver);
        return "driver/update-driver";
    }

    @PostMapping(value = "/update-driver")
    public String updateDriver(@ModelAttribute Driver driver, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        driverService.updateById(driver);
        return "redirect:" + referer;
    }

    @GetMapping("/driver/{driverId}")
    public String deleteDriver(@PathVariable("driverId") int driverId) {
        driverService.deleteDriver(driverId);
        return "redirect:/view-driver";
    }


    @GetMapping("/view-driver/{oneDriver}")
    public String viewOne(@PathVariable("oneDriver") int id, Model model) {
        model.addAttribute("oneDriver", driverService.findById(id));
        return "driver/one-driver";
    }

}
