package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.Address;
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

    public void addNewDriver(Driver driver, Address address) {
        driverRepo.addNewDriver(driver, address);
    }

    public Driver findById(int id){
        return driverRepo.findById(id);
    }

    public void updateById(int id, Driver driver){
        driverRepo.updateById(id, driver);
    }

    public void deleteDriver(int driverId){
        driverRepo.deleteDriver(driverId);
    }
}
