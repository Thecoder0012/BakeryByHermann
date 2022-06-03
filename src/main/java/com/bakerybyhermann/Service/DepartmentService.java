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

    public int[] departmentIds (){
        int departmentsCount = fetchAll().size();
        int[] departmentIds = new int[departmentsCount];
        for (int i = 0; i < departmentsCount; i++) {
            departmentIds[i] = fetchAll().get(i).getDepartmentId();
        }
        return departmentIds;
    }
}
