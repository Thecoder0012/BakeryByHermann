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
}
