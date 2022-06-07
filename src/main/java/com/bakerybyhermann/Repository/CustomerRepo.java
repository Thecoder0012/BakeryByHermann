package com.bakerybyhermann.Repository;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Person;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
Dette er en repository klasse, som vi bruger til at tale med databasen.
 */
@Repository
public class CustomerRepo {

    // Her opretter vi en instans af en JdbcTemplate klasse.
    @Autowired
    JdbcTemplate jdbcTemplate;


    //Alt hvad der bruges i fetchAll
    public List<Customer> fetchAll() {

        String sql = "SELECT customer_tbl.customer_id, person_tbl.person_id as personId,first_name,last_name," + "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,repeated_visits,company_name\n" +
                "FROM person_tbl\n" +
                "INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";

        RowMapper rowMapper = new CustomerMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

//Alt hvad bruges addNew
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

    public void addNew(Customer customer, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql2 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql2, customer.getFirstName(), customer.getLastName(), getAddressId(), customer.getPhoneNumber(),
                customer.getEmail());

        String sql1 = "INSERT INTO customer_tbl(person_id, repeated_visits, company_name)" + " VALUES (?,?,?)";
        jdbcTemplate.update(sql1, getPersonId(), customer.getRepeatedVisits(), customer.getCompanyName());

    }


//Alt hvad der bruges i update by ID
    public void updateById(int id, Customer c) {

        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, c.getAddress().getStreetName(), c.getAddress().getStreetNumber(), c.getAddress().getZipCode(),
                getUpdateAddressId(id));

        String sqlCustomer = "UPDATE customer_tbl SET repeated_visits = ?, company_name = ? WHERE person_id = ?";
        jdbcTemplate.update(sqlCustomer, c.getRepeatedVisits(), c.getCompanyName(), id);

        String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? " +
                "WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, c.getFirstName(), c.getLastName(), c.getPhoneNumber(),c.getEmail(), id);

    }

    public int getUpdateAddressId(int id) {
        String sql = "SELECT person_tbl.address_id, street_name,street_number,zip_code_tbl.zip_code,city FROM person_tbl \n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id \n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "WHERE person_id = ?";

        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return address.getAddressId();
    }

    public Customer findById(int id) {

        String sql = "SELECT customer_tbl.customer_id , person_tbl.person_id as personId,first_name,last_name," +
                "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,repeated_visits,company_name\n" +
                "FROM person_tbl\n" +
                "INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code WHERE customer_tbl.customer_id = ?";

        RowMapper rowMapper = new CustomerMapper();

        List<Customer> customerList = jdbcTemplate.query(sql,rowMapper,id);
        return customerList.get(0);
    }


//Alt hvad der bruges i delete

    public void delete(int personId) {
        String sql = "DELETE customer_tbl,person_tbl,address_tbl FROM customer_tbl \n" +
                "INNER JOIN person_tbl ON customer_tbl.person_id = person_tbl.person_id \n" +
                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
                "WHERE customer_tbl.person_id = ?";

        jdbcTemplate.update(sql, personId);
    }

}
