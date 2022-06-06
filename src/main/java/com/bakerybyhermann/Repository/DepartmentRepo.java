package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Department;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import com.bakerybyhermann.Repository.Mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Department> fetchAll () {
        String sql = "SELECT department_id, location, short_name, " +
                "ad.street_name, ad.street_number, zt.zip_code, city FROM department_tbl dp " +
                "JOIN address_tbl ad ON dp.address_id = ad.address_id " +
                "JOIN zip_code_tbl zt ON ad.zip_code = zt.zip_code";

        RowMapper rowMapper = new DepartmentMapper();
        return jdbcTemplate.query(sql, rowMapper);

    }

    public int getAddressId() {
        String sql = "SELECT address_id,street_name,street_number," +
                "zip_code_tbl.zip_code, zip_code_tbl.city FROM address_tbl,  zip_code_tbl ORDER BY address_id DESC LIMIT 1";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper);
        return address.getAddressId();
    }

    public void addNew(Department department, Address address) {

        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

        String sql2 = "INSERT INTO department_tbl(department_id, location, short_name, address_id) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql2, department.getDepartmentId(), department.getLocationName(), department.getShortName(), getAddressId());

    }

    public void updateById(int id, Department department) {

        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sqlAddress, department.getAddress().getStreetName(), department.getAddress().getStreetNumber(), department.getAddress().getZipCode(), getUpdateAddressId(id));

        String sqlDepartment = "UPDATE department_tbl SET location = ?, short_name = ? WHERE department_id = ?";
        jdbcTemplate.update(sqlDepartment, department.getLocationName(), department.getShortName(), id);

    }

    public int getUpdateAddressId(int id) {
        String sql = "SELECT department_tbl.address_id, street_name,street_number,zip_code_tbl.zip_code,city FROM department_tbl \n" +
                "INNER JOIN address_tbl ON department_tbl.address_id = address_tbl.address_id \n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "WHERE department_id = ?";

        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return address.getAddressId();
    }

    public Department findById(int id) {

        String sql = "SELECT department_tbl.department_id, location, short_name, street_name, street_number, address_tbl.zip_code, city" +
                "FROM department_tbl\n" +
                "INNER JOIN address_tbl ON department_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code WHERE department_tbl.department_id = ?";

        RowMapper rowMapper = new DepartmentMapper();

        List<Department> departmentList = jdbcTemplate.query(sql,rowMapper,id);
        return departmentList.get(0);
    }

    public void delete(int departmentId) {
        String sql = "DELETE department_tbl,address_tbl FROM department_tbl \n" +
                "INNER JOIN address_tbl on department_tbl.address_id = address_tbl.address_id\n" +
                "WHERE department_tbl.department_id = ?";

        jdbcTemplate.update(sql, departmentId);
    }
}
