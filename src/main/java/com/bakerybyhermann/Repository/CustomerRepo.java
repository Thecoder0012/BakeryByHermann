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
        int idid = getAddressId();
        return jdbcTemplate.query(sql,rowMapper);
    }

    public int getAddressId(){
        String sql = "SELECT customer_id, address_id\n" +
                "   FROM customer_tbl \n" +
                "                JOIN address_tbl  ON  address_tbl.address_id = customer_tbl.address_id \n" +
                "                ";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        List<Customer> cuscus =  jdbcTemplate.query(sql,rowMapper);
        for (int i = 0; i < cuscus.size(); i++) {
            System.out.println(cuscus.get(i).getAddress());
        }
        return 0;

    }

    public void addNew (Customer customer) {
        String sql2 = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";

        String sql = "INSERT INTO customer_tbl(first_name,last_name,address_id,phone_number,e_mail, repeated_visits, company_name)" +
                " VALUES (?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql2,customer.getAddress()
                ,3,customer.getZipCode());

        jdbcTemplate.update(sql,customer.getFirstName(),customer.getLastName(),6,customer.getPhoneNumber(),
                customer.getEmail(),customer.getRepeatedVisits(),customer.getCompanyName());


       //
        //+" INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?) ";
        System.out.println(customer.getFirstName()+customer.getLastName()+customer.getPhoneNumber()+
                customer.getEmail()+customer.getRepeatedVisits()+customer.getCompanyName()+customer.getAddress()
                +customer.getZipCode());

        /*jdbcTemplate.update(sql2,customer.getAddress()
                ,3,customer.getZipCode() );*/



//,customer.getAddress()
//                ,3,customer.getZipCode()

    }

}
