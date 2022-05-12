package com.bakerybyhermann.Repository;


import com.bakerybyhermann.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAll(){
        String sql = "SELECT customer_id, first_name, last_name, CONCAT(street_name, ' ', street_number) as address, zip_code_tbl.zip_code, city, phone_number, e_mail as email, repeated_visits, company_name\n" +
                "   FROM customer_tbl \n" +
                "                JOIN address_tbl  ON  address_tbl.address_id = customer_tbl.address_id \n" +
                "                JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.query(sql,rowMapper);
    }



}
