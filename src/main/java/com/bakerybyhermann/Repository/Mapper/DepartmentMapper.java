package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Department department = new Department();

        Address address = new Address();

        department.setDepartmentId(rs.getInt("department_id"));
        department.setLocationName(rs.getString("location"));
        department.setShortName(rs.getString("short_name"));

        address.setStreetName(rs.getString("street_name"));
        address.setStreetNumber(rs.getInt("street_number"));
        address.setZipCode(rs.getInt("zip_code"));
        address.setCity(rs.getString("city"));

        department.setAddress(address);
        //System.out.println("MAPPER ROW");
        return department;
    }
}
