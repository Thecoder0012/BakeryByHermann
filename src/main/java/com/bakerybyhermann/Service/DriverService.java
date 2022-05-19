package com.bakerybyhermann.Service;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Driver;
import com.bakerybyhermann.Repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    DriverRepo driverRepo;

    public List<Driver> fetchAll(){
        return driverRepo.fetchAll();
    }
    /*public void addNew(Driver driver, Address address) {
        driverRepo.addNew(driver, address);
    }*/
}
