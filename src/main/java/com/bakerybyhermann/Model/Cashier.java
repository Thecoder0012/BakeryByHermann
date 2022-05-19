package com.bakerybyhermann.Model;

import java.util.List;

public class Cashier extends Employee{

        private List<String> languages;
        private boolean isOver18;

    public Cashier(int personId, String firstName, String lastName, Address address, int phoneNumber, String email, int employeeId, int age, boolean gender, boolean fullTimeEmployee, List<String> languages, boolean isOver18) {
        super(personId, firstName, lastName, address, phoneNumber, email, employeeId, age, gender, fullTimeEmployee);
        this.languages = languages;
        this.isOver18 = isOver18;
    }

    public Cashier() {
    }
}
