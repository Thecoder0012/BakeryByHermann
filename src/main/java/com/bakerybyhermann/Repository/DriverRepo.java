package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.*;
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

    public int getEmployeeId() {
        String sql = "SELECT * FROM employee_tbl ORDER BY employee_id DESC LIMIT 1";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper);
        return employee.getEmployeeId();
    }

    public void addNewDriver(Driver driver, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql1 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql1,  driver.getFirstName(), driver.getLastName(), getAddressId(), driver.getPhoneNumber(),
                driver.getEmail());

        String sql2 = "INSERT INTO employee_tbl(person_id, age, gender, fulltime_employee) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql2, getPersonId(), driver.getAge(), driver.isGender(), driver.isFullTimeEmployee());

        String sql3 = "INSERT INTO driver_tbl(employee_id, driver_license_number, registration_number) VALUES (?,?,?)";
        jdbcTemplate.update(sql3, getEmployeeId(), driver.getDriverLicenseNumber(), driver.getRegistrationNumber());

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

    public Driver findById(int id) {

        String sql = "SELECT person_tbl.person_id as personId, driver_id, first_name,last_name,street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city,  " +
                "phone_number,e_mail as email, driver_license_number, registration_number, employee_tbl.employee_id, age, gender, fulltime_employee\n" +
                "FROM person_tbl\n" +
                "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "INNER JOIN driver_tbl ON employee_tbl.employee_id = driver_tbl.employee_id\n" +
                "WHERE person_tbl.person_id = ?";

        RowMapper rowMapper = new DriverMapper();

        List<Driver> driverList = jdbcTemplate.query(sql,rowMapper,id);
        return driverList.get(0);
    }

    public void updateById(Driver driver) {

        String sqlDriver = "UPDATE driver_tbl SET driver_license_number = ?, registration_number = ? WHERE driver_id = ?";
        jdbcTemplate.update(sqlDriver, driver.getDriverLicenseNumber(), driver.getRegistrationNumber(), getDriverId());


        String sqlEmployee = "UPDATE employee_tbl SET age = ?, gender = ?, fulltime_employee = ? WHERE employee_id = ?";
        jdbcTemplate.update(sqlEmployee, driver.getAge(), driver.isGender(), driver.isFullTimeEmployee(), getEmployeeId());


        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, driver.getAddress().getStreetName(), driver.getAddress().getStreetNumber(), driver.getAddress().getZipCode(),getAddressId());

        String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, driver.getFirstName(), driver.getLastName(), driver.getPhoneNumber(),driver.getEmail(), driver.getPersonId());

    }

    public int getDriverId() {
        String sql = "SELECT * FROM driver_tbl ORDER BY driver_id DESC LIMIT 1";
        RowMapper<Driver> rowMapper = new BeanPropertyRowMapper<>(Driver.class);
        Driver driver = jdbcTemplate.queryForObject(sql, rowMapper);
        return driver.getDriverId();
    }


    public void deleteDriver(int driverId) {
        String sql = "DELETE driver_tbl,employee_tbl,person_tbl,address_tbl FROM driver_tbl \n" +
                "INNER JOIN employee_tbl ON driver_tbl.employee_id = employee_tbl.employee_id \n" +
                "INNER JOIN person_tbl ON employee_tbl.person_id = person_tbl.person_id\n" +
                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
                "WHERE driver_tbl.driver_id = ?";

//        String sql1 = "DELETE cashier_tbl,employee_tbl,person_tbl,address_tbl FROM cashier_tbl \n" +
//                "INNER JOIN employee_tbl ON cashier_tbl.employee_id = employee_tbl.employee_id \n" +
//                "INNER JOIN person_tbl ON employee_tbl.person_id = person_tbl.person_id\n" +
//                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
//                "WHERE cashier_tbl.cashier_id = ?;";
//
//        jdbcTemplate.update(sql, cashierId);

        jdbcTemplate.update(sql, driverId);
    }

}
