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


    //Den får hvor mange afdelinger vi har via int departmentCount.
    private ArrayList<ArrayList<Order>> ordersSeperated (int departmentsCount) {
        //opretter den en ny ArrayList, som indeholder antal afdelinger ArrayLists, som indeholder
        //bestillingerne fordelt på afhentnings afdeling
        ArrayList<ArrayList<Order>> ordersSeperated = new ArrayList<>();
        //her looper vi og fordeler på de afdelinger vi har tilrådighed
        for (int i = 0; i < departmentsCount; i++) {
            ordersSeperated.add(new ArrayList<Order>());
        }//Nu har den bare tre tomme ArrayLists i den store ArrayList
        return ordersSeperated;//den returnere tomme Arraylister

        //This method takes the count of departments as parameter. It then returns that amount
        //in empty ArrayLists. These lists would be used to seperate the orders by their
        //Pick-up location
    }

    //Her får den en int[] som indeholder afdelingernes id numre
    private ArrayList<ArrayList<Order>> populateOrders (int departmentsCount, int[] departmentIds){
        //Nu går den ind og henter alle bestillingerne
        List<Order> orders = fetchAll();
        //så henter jeg den ArrayList med de tomme ArrayLists
        ArrayList<ArrayList<Order>> seperatedByDepartment = ordersSeperated(departmentsCount);

        //Så traversere vi, alle bestillingerne, og fordeler dem således:
        for (int i = 0; i < departmentsCount; i++) {//den kører departmentsCount gange
            for (int j = 0; j < orders.size(); j++) {//den kører antal bestillinger i systemet
                if(orders.get(j).getPickupLocation().getDepartmentId()==departmentIds[i]){
                    //Alle dem der har pick-up-lokation departmentId, bliver gemt:
                    //i den liste som matcher
                    seperatedByDepartment.get(i).add(orders.get(j));
                }
            }
        }
        return seperatedByDepartment;
        //This method takes all orders, and the lists representing the departments
        //then it pairs all the orders, with pick-up-department, that match together,
        //And returns a list of lists that contain orders in the right seperation.
    }

    //Den gør listerne klar til at blive sendt til controlleren
    public ArrayList<ArrayList<Order>> fetchAllSorted (int departmentsCount, int[] departmentIds) {

        return populateOrders(departmentsCount, departmentIds);
        //This method is only responsible to return the ArrayList, which would be called
        //from the Controller
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

    public List<Order> fetchArchived (){
        return orderRepo.fetchArchived();
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
