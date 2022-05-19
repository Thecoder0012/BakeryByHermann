package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Cashier;
import com.bakerybyhermann.Model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Cashier cashier = new Cashier();

        Address address = new Address();

        cashier.setPersonId(rs.getInt("personId"));
        cashier.setCashierId(rs.getInt("cashierId"));
        cashier.setFirstName(rs.getString("first_name"));
        cashier.setLastName(rs.getString("last_name"));
        cashier.setPhoneNumber(rs.getInt("phone_number"));
        cashier.setEmail(rs.getString("email"));
        cashier.setEmployeeId(rs.getInt("employeeId"));
        cashier.setAge(rs.getInt("age"));
        cashier.setGender(rs.getBoolean("gender"));
        cashier.setFullTimeEmployee(rs.getBoolean("fulltime_employee"));
        cashier.setCoffeeDiplom(rs.getBoolean("coffee_diplom"));


        address.setStreetName(rs.getString("streetName"));
        address.setStreetNumber(rs.getInt("streetNumber"));
        address.setZipCode(rs.getInt("address_tbl.zip_code"));
        address.setCity(rs.getString("city"));


        cashier.setAddress(address);

        return cashier;
    }
}
