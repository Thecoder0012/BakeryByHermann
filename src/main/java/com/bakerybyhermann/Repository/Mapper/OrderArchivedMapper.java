package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Department;
import com.bakerybyhermann.Model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderArchivedMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();
        Customer customer = new Customer();
        Department department = new Department();

        customer.setFirstName(rs.getString("customer_name"));
        customer.setLastName(rs.getString("customer_lname"));
        customer.setPhoneNumber(rs.getInt("customer_phone"));

        department.setShortName(rs.getString("delivery_department_shortname"));

        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(LocalDate.now());//FEJL
        order.setPickupDateAndTime(rs.getString("pickup_time"));
        order.setTotalPrice(rs.getInt("total_price"));

        order.setCustomer(customer);
        order.setPickupLocation(department);

        return order;
    }
}
