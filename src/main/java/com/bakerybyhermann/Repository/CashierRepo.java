package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Cashier;
import com.bakerybyhermann.Repository.Mapper.CashierMapper;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CashierRepo {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Cashier> fetchAll() {
        String sql = "SELECT cashier_id as cashierId, person_tbl.person_id as personId,employee_tbl.employee_id as employeeId,first_name,last_name," + "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,age,gender,employee_tbl.fulltime_employee,coffee_diplom\n" +
                "FROM person_tbl\n" +
                "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n " +
                "INNER JOIN  cashier_tbl ON employee_tbl.employee_id = cashier_tbl.employee_id";
        RowMapper rowMapper = new CashierMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }


    public void delete(int cashierId){
        String sql = "DELETE cashier_tbl,employee_tbl,person_tbl,address_tbl FROM cashier_tbl \n" +
                "INNER JOIN employee_tbl ON cashier_tbl.employee_id = employee_tbl.employee_id \n" +
                "INNER JOIN person_tbl ON employee_tbl.person_id = person_tbl.person_id\n" +
                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
                "WHERE cashier_tbl.cashier_id = ?;";

        jdbcTemplate.update(sql, cashierId);
    }

//    public void addNew(Cashier cashier){
//        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
//        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());
//
//        String sql2 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
//        jdbcTemplate.update(sql2, customer.getFirstName(), customer.getLastName(), getAddressId(), customer.getPhoneNumber(),
//                customer.getEmail());
//
//        String sql1 = "INSERT INTO customer_tbl(person_id, repeated_visits, company_name)" + " VALUES (?,?,?)";
//        jdbcTemplate.update(sql1, getPersonId(), customer.getRepeatedVisits(), customer.getCompanyName());
//    }


}
