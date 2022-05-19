package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.Product;
import com.bakerybyhermann.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> fetchAll(){
        return productRepo.fetchAll();
    }

    public void addNew (Product p){
        productRepo.addNew(p);
    }

    public Product findById(int id){
        return productRepo.findById(id);
    }

    public void updateById(int id, Product product){
        productRepo.updateById(id, product);
    }

    public void delteById(int id){
        productRepo.delteById(id);
    }
}
