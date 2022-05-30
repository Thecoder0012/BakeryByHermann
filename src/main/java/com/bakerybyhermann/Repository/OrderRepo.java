package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.*;
import com.bakerybyhermann.Repository.Mapper.OrderMapper;
import com.bakerybyhermann.Repository.Mapper.OrderMapperProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ProductList> fetchProducts(int orderId) {
        String sql = "SELECT product_tbl.product_id as pId, product_name, price, quantity \n" +
                "FROM in_list\n" +
                "JOIN product_tbl\n" +
                "ON product_tbl.product_id = in_list.product_id\n" +
                "WHERE order_id = ?";
        RowMapper rowMapper = new OrderMapperProducts();
        return jdbcTemplate.query(sql, rowMapper, orderId);
    }

    public List<Order> fetchAll(){
        String sql = "SELECT order_id, order_date, " +
                "order_department_id, dpo.location as order_loc_name, dpo.short_name as order_loc_short, " +
                "addt.street_name as od_loc_street, addt.street_number as od_loc_streetNum, \n" +
                "addt.zip_code as odloc_zip, dpozt.city as odloc_city, " +
                "delivery_department_id, dpl.location as pick_loc_name, dpl.short_name as pick_loc_short, " +
                "addtl.street_name as pick_street, addtl.street_number as pick_streetNum,\n" +
                "addtl.zip_code as pick_zip,  dplzt.city as pick_city, pickup_time, \n" +
                "ao.customer_id as cust_id, pt.first_name as cus_name, pt.last_name as cus_lname, " +
                "pt.phone_number as cus_phone, pt.e_mail as cus_mail, att.street_name as cus_street,  " +
                "att.street_number as cus_streetnum,  att.zip_code as cus_zip, zt.city as cus_city,\n" +
                "ao.cashier_id as cash_id, ptch.first_name as cash_name," +
                " ptch.last_name as cash_lname, ptch.phone_number as cash_phone, " +
                "ptch.e_mail cash_mail, " +
                "attch.street_name as cash_street, attch.street_number as cash_streetnum, " +
                "attch.zip_code cash_zip, ztch.city as cash_city,\n" +
                "payment_status, special_status, total_price \n" +
                " FROM active_orders ao\n" +
                "JOIN department_tbl dpo ON ao.order_department_id = dpo.department_id\n" +
                "JOIN address_tbl addt ON addt.address_id = dpo.address_id\n" +
                "JOIN zip_code_tbl dpozt ON dpozt.zip_code = addt.zip_code\n" +
                "JOIN department_tbl dpl ON ao.delivery_department_id = dpl.department_id\n" +
                "JOIN address_tbl addtl ON addtl.address_id = dpl.address_id\n" +
                "JOIN zip_code_tbl dplzt ON dplzt.zip_code = addtl.zip_code\n" +
                "JOIN customer_tbl ct ON ao.customer_id = ct.customer_id\n" +
                "JOIN person_tbl pt ON ct.person_id = pt.person_id\n" +
                "JOIN address_tbl att ON att.address_id = pt.address_id\n" +
                "JOIN zip_code_tbl zt ON zt.zip_code = att.zip_code\n" +
                "JOIN cashier_tbl cht ON ao.cashier_id = cht.cashier_id\n" +
                "                JOIN employee_tbl emt ON emt.employee_id = cht.employee_id\n" +
                "                JOIN person_tbl ptch ON ptch.person_id = emt.person_id\n" +
                "                JOIN address_tbl attch ON attch.address_id = ptch.address_id\n" +
                "                JOIN zip_code_tbl ztch ON attch.zip_code = ztch.zip_code";

        RowMapper rowMapper = new OrderMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    private int getorderId (){
        String sql = "SELECT order_id FROM active_orders ORDER BY order_id DESC LIMIT 1";
        RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
        List<Order> orders = jdbcTemplate.query(sql, rowMapper);
        return orders.get(0).getOrderId();
    }

    public void addToList(ProductList p){
        String sql = "INSERT INTO in_list (order_id, product_id, quantity) VALUES (?,?,?)";
        jdbcTemplate.update(sql, getorderId(), p.getProduct().getProductId(), p.getQuantity());
    }

    public void addNew (Order o){
        o.setOrderDate(LocalDate.now());
        //o.getPickupDateAndTime().replace('T', ' ');

        System.out.println("Order date: "+o.getOrderDate());//Correct
        System.out.println("Order dep.: "+o.getOrderLocation().getShortName());//Correct
        System.out.println("Order dep. Id: "+o.getOrderLocation().getDepartmentId());
        System.out.println("Pick-up dep.: "+o.getPickupLocation().getShortName());//Correct
        System.out.println("Pick-up dep. Id: "+o.getPickupLocation().getDepartmentId());
        System.out.println("Pick-up time: "+o.getPickupDateAndTime().replace('T', ' '));//50%
        System.out.println("Customer name: "+o.getCustomer().getFirstName());//Mangler at display korrekt
        System.out.println("Customer Id: "+o.getCustomer().getCustomerId());
        System.out.println("Cashier name: "+o.getCashier().getFirstName());
        System.out.println("Cashier Id: "+o.getCashier().getCashierId());
        System.out.println("Payed status: "+o.isPayed());
        System.out.println("Special order: "+o.isSpecialOrder());
        System.out.println("Total price: "+o.getTotalPrice());

        String sqlOrder = "INSERT INTO active_orders (order_date, order_department_id, " +
                "delivery_department_id, pickup_time, customer_id, cashier_id, payment_status, " +
                "special_status, total_price) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sqlOrder, o.getOrderDate(), o.getOrderLocation().getDepartmentId(),
                o.getPickupLocation().getDepartmentId(),
                o.getPickupDateAndTime().replace('T', ' '), o.getCustomer().getCustomerId(),
                o.getCashier().getCashierId(),
                o.isPayed(), o.isSpecialOrder(), o.getTotalPrice());

        //den skal loope alt efter hvor mange slags produkter der er valgt
        String sqlOrderProducts = "INSERT INTO in_list (order_id, product_id, quantity) VALUES (?,?,?)";
        //jdbcTemplate.update(sqlOrderProducts, );
/*
        int orderId = getorderId();
        System.out.println("Is the returned order_id correct? order_id: ");

        //den skal loope så mange gange som der er i en liste
        //listen indeholder info om order_id, et product_id, quantity
        String sqlForInList = "INSERT INTO in_list (order_id, product_id, quantity) VALUES (?, ?, ?);";
        jdbcTemplate.update(sqlForInList, orderId, o.getProductList().getProduct().getProductId(),
                o.getProductList().getQuantity());

*/

    }

    public Order findById (int id){
        return null;
    }

    public void updateById (int id, Order order){}

    public void deleteById (int id){}
}
