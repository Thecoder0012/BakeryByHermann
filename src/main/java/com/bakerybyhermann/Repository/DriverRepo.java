package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Driver;
import com.bakerybyhermann.Model.Person;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import com.bakerybyhermann.Repository.Mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Driver> fetchAll(){

        String sql = "SELECT person_tbl.person_id as personId, driver_id, first_name,last_name,street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city,  " +
        "phone_number,e_mail as email, driver_license_number, registration_number, employee_tbl.employee_id, age, gender, fulltime_employee\n" +
                "FROM person_tbl\n" +
                "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "INNER JOIN driver_tbl ON employee_tbl.employee_id = driver_tbl.employee_id";

        RowMapper rowMapper = new DriverMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int getAddressId() {//bruges TIL ADD-NEW
        String sql = "SELECT address_id,street_name,street_number," +
                "zip_code_tbl.zip_code, zip_code_tbl.city FROM address_tbl,  zip_code_tbl ORDER BY address_id DESC LIMIT 1";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper);
        return address.getAddressId();
    }

    public int getPersonId() {
        String sql = "SELECT * FROM person_tbl ORDER BY person_id DESC LIMIT 1";
        RowMapper<Person> rowMapper = new BeanPropertyRowMapper<>(Person.class);
        Person person = jdbcTemplate.queryForObject(sql, rowMapper);
        return person.getPersonId();
    }


    public void addNew(Driver driver, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql2 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql2, driver.getFirstName(), driver.getLastName(), getAddressId(), driver.getPhoneNumber(),
                driver.getEmail());


    }



    public void addNew(Customer customer, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql2 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql2, customer.getFirstName(), customer.getLastName(), getAddressId(), customer.getPhoneNumber(),
                customer.getEmail());

        String sql1 = "INSERT INTO customer_tbl(person_id, repeated_visits, company_name)" + " VALUES (?,?,?)";
        jdbcTemplate.update(sql1, getPersonId(), customer.getRepeatedVisits(), customer.getCompanyName());

    }
}
