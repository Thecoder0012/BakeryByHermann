package com.bakerybyhermann.Service;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
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

    public void addNew(Department department, Address address) {
        departmentRepo.addNew(department, address);
    }

    public Department findById(int id){
        return departmentRepo.findById(id);
    }

    public void updateById(int id, Department department){
        departmentRepo.updateById(id, department);
    }

    public void delete(int departmentId){
        departmentRepo.delete(departmentId);
    }
}
