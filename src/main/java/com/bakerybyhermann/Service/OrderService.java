package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.*;
import com.bakerybyhermann.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    public List<Order> fetchAll(){
        List<Order> orders = orderRepo.fetchAll();

        for (int i = 0; i < orders.size(); i++) {
            int orderId = orders.get(i).getOrderId();
            List<ProductList> inList = orderRepo.fetchProducts(orderId);
            orders.get(i).setProductList(inList);
        }

        return orders;
    }

    public void addNew (Order o){
        orderRepo.addNew(o);
    }
    public void addToList(ProductList p){
        System.out.println("Inside Service ");
        orderRepo.addToList(p);
    }


    public Order findById (int id){
        return orderRepo.findById(id);
    }

    public void updateById (int id, Order order){}

    public void deleteById (int id){}
}

/*
public List<Order> fetchAll (){
        return null;
    }

    public void addNew (Order o){}

    public Order findById (int id){
        return null;
    }

    public void updateById (int id, Order order){}

    public void deleteById (int id){}
 */
