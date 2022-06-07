package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.*;
import com.bakerybyhermann.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    public ArrayList<ArrayList<Order>> fetchAllSorted (int departmentsCount, int[] departmentIds) {
        /*for (int i = 0; i < ordersSorted(departmentsCount, departmentIds).get(1).size(); i++) {
            System.out.println("Ordered: "+ordersSorted(departmentsCount, departmentIds).get(1).get(i).getPickupDateAndTime());
        }
        System.out.println();
        for (int i = 0; i < populateOrders(departmentsCount,departmentIds).get(1).size(); i++) {
            System.out.println("UnOrdered " + populateOrders(departmentsCount,departmentIds).get(1).get(i).getPickupDateAndTime());
        }
        System.out.println();*/
        return ordersSorted(departmentsCount, departmentIds);
        //This method is only responsible to return the ArrayList, which would be called
        //from the Controller
    }
    private ArrayList<ArrayList<Order>> ordersSorted (int depCount, int[] depIds){

        for (int i = 0; i < depCount; i++) {
            Collections.sort(populateOrders(depCount,depIds).get(i));
        //    for (int j = 0; j < populateOrders(departmentsCount, departmentIds).get(i).size(); j++) {
        //        populateOrders(departmentsCount, departmentIds).get(i).get(j).getPickupDateAndTime().
        //    }
        }
        return populateOrders(depCount,depIds);

        //This mehtod sorts the orders, on pick-up-time
    }

    private ArrayList<ArrayList<Order>> populateOrders (int departmentsCount, int[] departmentIds){
        List<Order> orders = fetchAll();
        ArrayList<ArrayList<Order>> seperatedByDepartment = ordersSeperated(departmentsCount);
        for (int i = 0; i < departmentsCount; i++) {
            for (int j = 0; j < orders.size(); j++) {
                if(orders.get(j).getPickupLocation().getDepartmentId()==departmentIds[i]){
                    seperatedByDepartment.get(i).add(orders.get(j));
                }
            }
        }
        return seperatedByDepartment;
        //This method takes all orders, and the lists representing the departments
        //then it pairs all the orders, with pick-up-department, that match together,
        //And returns a list of lists that contain orders in the right seperation.
    }

    private ArrayList<ArrayList<Order>> ordersSeperated (int departmentsCount) {
        ArrayList<ArrayList<Order>> ordersSeperated = new ArrayList<>();
        for (int i = 0; i < departmentsCount; i++) {
            ordersSeperated.add(new ArrayList<Order>());
        }
        return ordersSeperated;
        //This method takes the count of departments as parameter. It then returns that amount
        //in empty ArrayLists. These lists would be used to seperate the orders by their
        //Pick-up location
    }

    public List<Order> fetchAll(){
        List<Order> orders = orderRepo.fetchAll();

        for (int i = 0; i < orders.size(); i++) {
            int orderId = orders.get(i).getOrderId();
            List<ProductList> inList = orderRepo.fetchProducts(orderId);
            orders.get(i).setProductList(inList);

    }
        return orders;
    }
    //private void populateInList(){}

    public void addNew (Order o){
        orderRepo.addNew(o);
    }
    public void addToList(ProductList p){
        orderRepo.addToList(p);
    }


    public Order findById (int id){
        Order order = orderRepo.findById(id);
            int orderId = order.getOrderId();
            List<ProductList> inList = orderRepo.fetchProducts(orderId);
            order.setProductList(inList);
        return order;
    }

    public void updateById (int id, Order order){
        orderRepo.updateById(id, order);
    }

    public void archiveOrder (int id, Order o){
        orderRepo.archiveOrder(id, o);
    }

    public void deleteById (int id){
        orderRepo.deleteById(id);
    }
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
