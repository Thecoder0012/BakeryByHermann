package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BakerMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Baker baker = new Baker();

        Address address = new Address();

        baker.setPersonId(rs.getInt("personId"));
        baker.setBakerId(rs.getInt("baker_id"));
        baker.setEmployeeId(rs.getInt("employee_id"));
        baker.setFirstName(rs.getString("first_name"));
        baker.setLastName(rs.getString("last_name"));
        baker.setPhoneNumber(rs.getInt("phone_number"));
        baker.setEmail(rs.getString("email"));
        baker.setYearsOfExperience(rs.getInt("years_of_experience"));
        baker.setAge(rs.getInt("age"));
        baker.setGender(rs.getBoolean("gender"));
        baker.setFullTimeEmployee(rs.getBoolean("fulltime_employee"));

        address.setStreetName(rs.getString("streetName"));
        address.setStreetNumber(rs.getInt("streetNumber"));
        address.setZipCode(rs.getInt("address_tbl.zip_code"));
        address.setCity(rs.getString("city"));


        baker.setAddress(address);

        return baker;
    }
}
