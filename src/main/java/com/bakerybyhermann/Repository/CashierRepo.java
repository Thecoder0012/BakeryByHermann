package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.*;
import com.bakerybyhermann.Repository.Mapper.CashierMapper;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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



    public void addNew(Cashier cashier, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql1 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql1, cashier.getFirstName(), cashier.getLastName(), getAddressId(), cashier.getPhoneNumber(),
                cashier.getEmail());

        String sql2 = "INSERT INTO employee_tbl(person_id, age, gender, fulltime_employee) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql2, getPersonId(), cashier.getAge(), cashier.isGender(), cashier.isFullTimeEmployee());

        String sql3 = "INSERT INTO cashier_tbl(cashier_id,employee_id, coffee_diplom) VALUES (?,?,?)";
        jdbcTemplate.update(sql3, cashier.getCashierId(), getEmployeeId(), cashier.isCoffeeDiplom());

    }


    public void updateById(Cashier cashier) {

        String sqlCashier = "UPDATE cashier_tbl SET coffee_diplom = ? WHERE cashier_id = ?";
        jdbcTemplate.update(sqlCashier, cashier.isCoffeeDiplom(), getCashierId());

        String sqlEmployee = "UPDATE employee_tbl SET age = ?, gender = ?, fulltime_employee = ? WHERE employee_id = ?";
        jdbcTemplate.update(sqlEmployee, cashier.getAge(), cashier.isGender(), cashier.isFullTimeEmployee(), getEmployeeId());


        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, cashier.getAddress().getStreetName(), cashier.getAddress().getStreetNumber(), cashier.getAddress().getZipCode(), getAddressId());

        String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, cashier.getFirstName(), cashier.getLastName(), cashier.getPhoneNumber(), cashier.getEmail(), cashier.getPersonId());


    }

    public void delete(int cashierId) {
        String sql = "DELETE cashier_tbl,employee_tbl,person_tbl,address_tbl FROM cashier_tbl \n" +
                "INNER JOIN employee_tbl ON cashier_tbl.employee_id = employee_tbl.employee_id \n" +
                "INNER JOIN person_tbl ON employee_tbl.person_id = person_tbl.person_id\n" +
                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
                "WHERE cashier_tbl.cashier_id = ?;";

        jdbcTemplate.update(sql, cashierId);
    }

    public int getEmployeeId() {
        String sql = "SELECT * FROM employee_tbl ORDER BY employee_id DESC LIMIT 1";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper);
        return employee.getEmployeeId();
    }

    public int getCashierId() {
        String sql = "SELECT * FROM cashier_tbl ORDER BY cashier_id DESC LIMIT 1";
        RowMapper<Cashier> rowMapper = new BeanPropertyRowMapper<>(Cashier.class);
        Cashier cashier = jdbcTemplate.queryForObject(sql, rowMapper);
        return cashier.getCashierId();
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

    public Cashier findById(int id) {

        String sql = "SELECT cashier_id as cashierId, person_tbl.person_id as personId,employee_tbl.employee_id as employeeId,first_name,last_name," +
                "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,age,gender,employee_tbl.fulltime_employee,coffee_diplom\n" +
                "FROM person_tbl\n" +
                "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n " +
                "INNER JOIN  cashier_tbl ON employee_tbl.employee_id = cashier_tbl.employee_id WHERE cashier_id = ?";
        RowMapper rowMapper = new CashierMapper();

        List<Cashier> cashierList = jdbcTemplate.query(sql, rowMapper, id);
        return cashierList.get(0);
    }


}
