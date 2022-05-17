package com.bakerybyhermann.Model;

public class Employee extends Customer {

    private int age;
    private boolean gender;
    private boolean fullTimeEmployee;

    public Employee(int personId, String firstName, String lastName, Address address, int zipCode, String city, int phoneNumber, String email, int repeatedVisits, String companyName, int age, boolean gender) {
        super(personId, firstName, lastName, address, zipCode, city, phoneNumber, email, repeatedVisits, companyName);
        this.age = age;
        this.gender = gender;
    }

    public Employee() {

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
}
