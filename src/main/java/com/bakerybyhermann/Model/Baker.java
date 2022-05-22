package com.bakerybyhermann.Model;

public class Baker extends Employee {
    private int bakerId;
    private int employeeId;
    private int yearsOfExperience;

        public Baker(int personId, String firstName, String lastName, Address address, int phoneNumber, String email, int employeeId, int age, boolean gender, boolean fullTimeEmployee, int bakerId, int yearsOfExperience)
        {super(personId, firstName, lastName, address, phoneNumber, email, employeeId, age, gender, fullTimeEmployee);
        this.bakerId = bakerId;
        this.employeeId = employeeId;
        this.yearsOfExperience = yearsOfExperience;
    }



    public Baker() {
    }

    public int getBakerId() {
        return bakerId;
    }

    public void setBakerId(int bakerId) {
        this.bakerId = bakerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}


