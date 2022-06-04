package com.bakerybyhermann.Model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Order implements Comparable<Order>{

    private int orderId;//auto increment
    private LocalDate orderDate;//auto
    private Cashier cashier;//drop down
    private Department orderLocation;//drop down
    private Customer customer;//search and drop down
    private Department pickupLocation;//drop down
    private String pickupDateAndTime;//calendar
    private boolean payed;//radio btn
    private boolean specialOrder;//radio btn
    private List<ProductList> productList;//view
    private int totalPrice;//auto

    public Order(int orderId, LocalDate orderDate, Cashier cashier, Department orderLocation,
                 Customer customer, Department pickupLocation, String pickupDateAndTime,
                 boolean payed, boolean specialOrder, List<ProductList> productList, int totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cashier = cashier;
        this.orderLocation = orderLocation;
        this.customer = customer;
        this.pickupLocation = pickupLocation;
        this.pickupDateAndTime = pickupDateAndTime;
        this.payed = payed;
        this.specialOrder = specialOrder;
        this.productList = productList;
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public Department getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(Department orderLocation) {
        this.orderLocation = orderLocation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Department getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Department pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPickupDateAndTime() {
        return pickupDateAndTime;
    }

    public void setPickupDateAndTime(String pickupDateAndTime) {
        this.pickupDateAndTime = pickupDateAndTime;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public boolean isSpecialOrder() {
        return specialOrder;
    }

    public void setSpecialOrder(boolean specialOrder) {
        this.specialOrder = specialOrder;
    }

    public List<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList> productList) {
        this.productList = productList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int compareTo(Order o) {
        if(!pickupDateAndTime.equalsIgnoreCase(o.pickupDateAndTime))
            return pickupDateAndTime.compareTo(o.pickupDateAndTime);

        else return 0;
    }
    /*
    boolean: produced, delivered, picked-up
     */
}

