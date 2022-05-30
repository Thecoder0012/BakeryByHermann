package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.Department;
import com.bakerybyhermann.Repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepo departmentRepo;

    public List<Department> fetchAll () {
        return departmentRepo.fetchAll();
    }
}
