package com.bakerybyhermann.Service;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Cashier;
import com.bakerybyhermann.Repository.CashierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashierService {

    @Autowired
    CashierRepo cashierRepo;

    public List<Cashier> fetchAll(){
       return cashierRepo.fetchAll();
    }

    public void delete(int cashierId){
        cashierRepo.delete(cashierId);
    }

    public void addNew(Cashier cashier,Address address){
        cashierRepo.addNew(cashier, address);
    }

    public Cashier findById(int cashierId) {
        return cashierRepo.findById(cashierId);
    }

    public void updateById(Cashier cashier){
        cashierRepo.updateById(cashier);
    }
}
