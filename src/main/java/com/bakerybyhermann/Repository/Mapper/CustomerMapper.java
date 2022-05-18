package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = new Customer();

        Address address = new Address();

        customer.setPersonId(rs.getInt("personId"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setPhoneNumber(rs.getInt("phone_number"));
        customer.setEmail(rs.getString("email"));
        customer.setRepeatedVisits(rs.getInt("repeated_visits"));
        customer.setCompanyName(rs.getString("company_name"));


        address.setStreetName(rs.getString("streetName"));
        address.setStreetNumber(rs.getInt("streetNumber"));
        address.setZipCode(rs.getInt("address_tbl.zip_code"));
        address.setCity(rs.getString("city"));


        customer.setAddress(address);

        return customer;
    }
}
