package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    @Autowired
    CustomerRepo customerRepo;

    public List<Customer> fetchAll(){
        return customerRepo.fetchAll();
    }

    public void addNew(Customer customer) {
        customerRepo.addNew(customer);
    }

    public Customer findById(int id){
        return customerRepo.findById(id);
    }

    public void updateById(int id, Customer c){
        customerRepo.updateById(id, c);
    }

    public void delete(int customerId){
        customerRepo.delete(customerId);
    }

}
