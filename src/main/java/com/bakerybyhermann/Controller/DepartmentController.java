package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Department;
import com.bakerybyhermann.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/departments")
    public String getDepartment(Model model){
        model.addAttribute("departmentsList",departmentService.fetchAll());
        return "department/view-departments";
    }

    @GetMapping("/new-department")
    public String createDepartment(){
        return "department/new-department";
    }

    @PostMapping("/new-department")
    public String createDepartment(@ModelAttribute Department department, @ModelAttribute Address address){
        department.setAddress(address);
        departmentService.addNew(department, address);
        return "redirect:/view-departments";
    }

    @GetMapping("/update-department/{id}")
    public String updateDepartment(@PathVariable("id") int id, Model model){
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "department/update-department";
    }

    @PostMapping("/update-department")
    public String updateDepartment(@ModelAttribute Department department, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        departmentService.updateById(department.getDepartmentId(), department);
        return "redirect:"+referer;
    }

    @GetMapping("/department/{departmentId}")
    public String deleteDepartment(@PathVariable("departmentId") int departmentId){
        departmentService.delete(departmentId);
        return "redirect:/departments";
    }

}
