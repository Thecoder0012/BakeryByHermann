package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();
        Department orderLocation = new Department();
        Address orderLocationAddress = new Address();
        Department pickupLocation = new Department();
        Address pickupLocationAddress = new Address();
        Customer customer = new Customer();
        Address customerAddress = new Address();
        Cashier cashier = new Cashier();
        Address cashierAddress = new Address();

        orderLocation.setDepartmentId(rs.getInt("order_department_id"));
        orderLocation.setLocationName(rs.getString("order_loc_name"));
        orderLocation.setShortName(rs.getString("order_loc_short"));
        orderLocationAddress.setStreetName(rs.getString("od_loc_street"));
        orderLocationAddress.setStreetNumber(rs.getInt("od_loc_streetNum"));
        orderLocationAddress.setZipCode(rs.getInt("odloc_zip"));
        orderLocationAddress.setCity(rs.getString("odloc_city"));
        orderLocation.setAddress(orderLocationAddress);

        pickupLocation.setDepartmentId(rs.getInt("delivery_department_id"));
        pickupLocation.setLocationName(rs.getString("pick_loc_name"));
        pickupLocation.setShortName(rs.getString("pick_loc_short"));
        pickupLocationAddress.setStreetName(rs.getString("pick_street"));
        pickupLocationAddress.setStreetNumber(rs.getInt("pick_streetNum"));
        pickupLocationAddress.setZipCode(rs.getInt("pick_zip"));
        pickupLocationAddress.setCity(rs.getString("pick_city"));
        pickupLocation.setAddress(orderLocationAddress);

        customer.setFirstName(rs.getString("cus_name"));
        customer.setLastName(rs.getString("cus_lname"));
        customer.setPhoneNumber(rs.getInt("cus_phone"));
        customer.setEmail(rs.getString("cus_mail"));
        customerAddress.setStreetName(rs.getString("cus_street"));
        customerAddress.setStreetNumber(rs.getInt("cus_streetnum"));
        customerAddress.setZipCode(rs.getInt("cus_zip"));
        customerAddress.setCity(rs.getString("cus_city"));
        customer.setAddress(customerAddress);

        cashier.setFirstName(rs.getString("cash_name"));
        cashier.setLastName(rs.getString("cash_lname"));
        cashier.setPhoneNumber(rs.getInt("cash_phone"));
        cashier.setEmail(rs.getString("cash_mail"));
        cashierAddress.setStreetName(rs.getString("cash_street"));
        cashierAddress.setStreetNumber(rs.getInt("cash_streetnum"));
        cashierAddress.setZipCode(rs.getInt("cash_zip"));
        cashierAddress.setCity(rs.getString("cash_city"));
        cashier.setAddress(cashierAddress);

        order.setOrderId(rs.getInt("order_id"));
        //Date rsDate = rs.getDate("order_date");
        //LocalDate newDate = LocalDate.parse((CharSequence) rsDate);
        order.setOrderDate(LocalDate.now());
//FEJL***
        order.setOrderLocation(orderLocation);
        order.setPickupDateAndTime(rs.getString("pickup_time"));
        order.setPickupLocation(pickupLocation);
        order.setCustomer(customer);
        order.setCashier(cashier);
        order.setPayed(rs.getBoolean("payment_status"));
        order.setSpecialOrder(rs.getBoolean("special_status"));
        order.setTotalPrice(rs.getInt("total_price"));



        return order;
    }
}
