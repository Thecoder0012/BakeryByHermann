package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Baker;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Repository.Mapper.BakerMapper;
import com.bakerybyhermann.Repository.Mapper.BakerMapper;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import com.bakerybyhermann.Repository.Mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.ValueStyler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BakerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Baker> fetchAll() {

        String sql = "SELECT person_tbl.person_id as personId, baker_id, first_name,last_name,street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city,  " +
                "phone_number,e_mail as email, years_of_experience, employee_tbl.employee_id, age, gender, fulltime_employee\n" +
                "FROM person_tbl\n" +
                "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "INNER JOIN baker_tbl ON employee_tbl.employee_id = baker_tbl.employee_id";

        RowMapper rowMapper = new BakerMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int getBakerId() {
        String sql = "SELECT baker_id, employee_id, years_of_experience FROM baker_tbl ORDER BY DESC LIMIT 1";
        RowMapper<Baker> rowMapper = new BeanPropertyRowMapper<>(Baker.class);
        Baker baker = jdbcTemplate.queryForObject(sql, rowMapper);
        return baker.getBakerId();
    }

    public void addNew(Baker baker) {
        String sql = "INSERT INTO baker_tbl (baker_id, employee_id, years_of_experience) VALUES (?,?,?";
        jdbcTemplate.update(sql, baker.getBakerId(), baker.getEmployeeId(), baker.getYearsOfExperience());

    }

    /*public void addNew(Customer customer, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql2 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql2, customer.getFirstName(), customer.getLastName(), getAddressId(), customer.getPhoneNumber(),
                customer.getEmail());

        String sql1 = "INSERT INTO customer_tbl(person_id, repeated_visits, company_name)" + " VALUES (?,?,?)";
        jdbcTemplate.update(sql1, getPersonId(), customer.getRepeatedVisits(), customer.getCompanyName());*/

   /* public void updateById(int id, Baker b) {

        String sqlBaker = "UPDATE baker_tbl SET baker_id = ?, employee_id = ?, years_of_experience = ?";
        jdbcTemplate.update(sqlBaker, b.getBakerId(), b.getEmployeeId(), b.getYearsOfExperience());

    }

    /*public void updateById(int id, Customer c) {

        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, c.getAddress().getStreetName(), c.getAddress().getStreetNumber(), c.getAddress().getZipCode(), getUpdateAddressId(id));

        String sqlCustomer = "UPDATE customer_tbl SET repeated_visits = ?, company_name = ? WHERE person_id = ?";
        jdbcTemplate.update(sqlCustomer, c.getRepeatedVisits(), c.getCompanyName(), id);

        String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, c.getFirstName(), c.getLastName(), c.getPhoneNumber(),c.getEmail(), id);

    }*/

    /*public int getUpdateBakerId (int id) {
        String sql = "SELECT baker_tbl, baker_id, employee_id, years_of_experience FROM baker_tbl"
    }

    /*public int getUpdateAddressId(int id) {
        String sql = "SELECT person_tbl.address_id, street_name,street_number,zip_code_tbl.zip_code,city FROM person_tbl \n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id \n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "WHERE person_id = ?";

        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return address.getAddressId();
    }*/




    }
