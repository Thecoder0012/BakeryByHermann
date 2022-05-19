package com.bakerybyhermann.Model;

public class Baker extends Employee {
    private int bakerId;
    private int employeeId;
    private int yearsOfExperience;

    public Baker(int bakerId, int employeeId, int yearsOfExperience) {
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
