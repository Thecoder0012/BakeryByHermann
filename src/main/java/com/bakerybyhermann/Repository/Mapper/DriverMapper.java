package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Driver;
import com.bakerybyhermann.Model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Driver driver = new Driver();

        Address address = new Address();

        driver.setPersonId(rs.getInt("personId"));
        driver.setDriverId(rs.getInt("driver_id"));
        driver.setEmployeeId(rs.getInt("employee_id"));
        driver.setFirstName(rs.getString("first_name"));
        driver.setLastName(rs.getString("last_name"));
        driver.setPhoneNumber(rs.getInt("phone_number"));
        driver.setEmail(rs.getString("email"));
        driver.setDriverLicenseNumber(rs.getString("driver_license_number"));
        driver.setRegistrationNumber(rs.getString("registration_number"));
        driver.setAge(rs.getInt("age"));
        driver.setGender(rs.getBoolean("gender"));
        driver.setFullTimeEmployee(rs.getBoolean("fulltime_employee"));

        address.setStreetName(rs.getString("streetName"));
        address.setStreetNumber(rs.getInt("streetNumber"));
        address.setZipCode(rs.getInt("address_tbl.zip_code"));
        address.setCity(rs.getString("city"));


        driver.setAddress(address);

        return driver;
    }
}
