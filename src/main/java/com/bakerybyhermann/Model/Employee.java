package com.bakerybyhermann.Model;

public class Employee extends Person {

    private int employeeId;
    private int age;
    private boolean gender;
    private boolean fullTimeEmployee;

    public Employee(int personId, String firstName, String lastName, Address address, int phoneNumber, String email, int employeeId, int age, boolean gender, boolean fullTimeEmployee) {
        super(personId, firstName, lastName, address, phoneNumber, email);
        this.employeeId = employeeId;
        this.age = age;
        this.gender = gender;
        this.fullTimeEmployee = fullTimeEmployee;
    }

    public Employee() {
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isFullTimeEmployee() {
        return fullTimeEmployee;
    }

    public void setFullTimeEmployee(boolean fullTimeEmployee) {
        this.fullTimeEmployee = fullTimeEmployee;
    }
}
