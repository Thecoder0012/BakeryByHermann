package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.*;
import com.bakerybyhermann.Repository.Mapper.BakerMapper;
import org.springframework.beans.factory.annotation.Autowired;
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


    public int getEmployeeId() {
        String sql = "SELECT * FROM employee_tbl ORDER BY employee_id DESC LIMIT 1";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper);
        return employee.getEmployeeId();
    }


    public void addNewBaker(Baker baker, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql1 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql1,  baker.getFirstName(), baker.getLastName(), getAddressId(), baker.getPhoneNumber(),
                baker.getEmail());

        String sql2 = "INSERT INTO employee_tbl( person_id, age, gender, fulltime_employee) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql2, getPersonId(), baker.getAge(), baker.isGender(), baker.isFullTimeEmployee());

    /*FEJL?*/    String sql3 = "INSERT INTO baker_tbl(employee_id, years_of_experience) VALUES (?,?)";
        jdbcTemplate.update(sql3, getEmployeeId(), baker.getYearsOfExperience());

    }


    //Alt hvad der bruges i update by ID

    public int getUpdateAddressId(int id) {
        String sql = "SELECT person_tbl.address_id, street_name,street_number,zip_code_tbl.zip_code,city FROM person_tbl \n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id \n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "WHERE person_id = ?";

        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return address.getAddressId();
    }



    public Baker findById(int id) {

        String sql = "SELECT baker_id, person_tbl.person_id as personId,employee_tbl.employee_id,first_name,last_name," +
                "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,age,gender,employee_tbl.fulltime_employee,years_of_experience\n" +
                "FROM person_tbl\n" +
                "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n " +
                "INNER JOIN  baker_tbl ON employee_tbl.employee_id = baker_tbl.employee_id WHERE baker_id = ?";


        RowMapper rowMapper = new BakerMapper();

        List<Baker> bakersList = jdbcTemplate.query(sql,rowMapper,id);
        return bakersList.get(0);
    }


    /*public void updateById(Baker baker) {

        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, baker.getAddress().getStreetName(), baker.getAddress().getStreetNumber(), baker.getAddress().getZipCode(), getUpdateAddressId(id));

        String sqlEmployee = "UPDATE employee_tbl SET age = ?, gender = ?, fulltime_employee = ? WHERE employee_id = ?";
        jdbcTemplate.update(sqlEmployee, baker.getAge(), baker.isGender(), baker.isFullTimeEmployee(), id);

        String sqlBaker = "UPDATE baker_tbl SET years_of_experience = ? WHERE baker_id = ?";
        jdbcTemplate.update(sqlBaker, baker.getYearsOfExperience(), id);

        String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, baker.getFirstName(), baker.getLastName(), baker.getPhoneNumber(),baker.getEmail(), id);

    }*/
    public void updateById(Baker baker) {

    String sqlBaker = "UPDATE baker_tbl SET years_of_experience = ? WHERE baker_id = ?";
        jdbcTemplate.update(sqlBaker, baker.getYearsOfExperience(), getBakerId());


    String sqlEmployee = "UPDATE employee_tbl SET age = ?, gender = ?, fulltime_employee = ? WHERE employee_id = ?";
        jdbcTemplate.update(sqlEmployee, baker.getAge(), baker.isGender(), baker.isFullTimeEmployee(), getEmployeeId());


    String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, baker.getAddress().getStreetName(), baker.getAddress().getStreetNumber(), baker.getAddress().getZipCode(),getAddressId());

    String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, baker.getFirstName(), baker.getLastName(), baker.getPhoneNumber(), baker.getEmail(), baker.getPersonId());
    }

    public int getBakerId() {
        String sql = "SELECT * FROM baker_tbl ORDER BY baker_id DESC LIMIT 1";
        RowMapper<Baker> rowMapper = new BeanPropertyRowMapper<>(Baker.class);
        Baker baker = jdbcTemplate.queryForObject(sql, rowMapper);
        return baker.getBakerId();
    }


//Alt hvad der bruges i delete

    public void deleteBaker(int bakerId) {
        String sql = "DELETE baker_tbl,employee_tbl,person_tbl,address_tbl FROM baker_tbl \n" +
                "INNER JOIN employee_tbl ON baker_tbl.employee_id = employee_tbl.employee_id \n" +
                "INNER JOIN person_tbl ON employee_tbl.person_id = person_tbl.person_id\n" +
                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
                "WHERE baker_tbl.baker_id = ?";

        jdbcTemplate.update(sql, bakerId);
    }

}



//    String sql = "SELECT person_tbl.person_id as personId, baker_id, first_name,last_name,street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city,  " +
//            "phone_number,e_mail as email, years_of_experience, employee_tbl.employee_id, age, gender, fulltime_employee\n" +
//            "FROM person_tbl\n" +
//            "INNER JOIN employee_tbl ON person_tbl.person_id = employee_tbl.person_id\n" +
//            "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
//            "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
//            "INNER JOIN baker_tbl ON employee_tbl.employee_id = baker_tbl.employee_id\n" +
//            "WHERE person_tbl.person_id = ?";