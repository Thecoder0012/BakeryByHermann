package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Department;
import com.bakerybyhermann.Repository.Mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

}
