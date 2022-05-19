package com.bakerybyhermann.Model;

import java.util.List;

public class Cashier extends Employee{

        private int cashierId;
        private boolean coffeeDiplom;

    public Cashier(int personId, String firstName, String lastName, Address address, int phoneNumber, String email, int employeeId, int age, boolean gender, boolean fullTimeEmployee, int cashierId, boolean coffeeDiplom) {
        super(personId, firstName, lastName, address, phoneNumber, email, employeeId, age, gender, fullTimeEmployee);
        this.cashierId = cashierId;
        this.coffeeDiplom = coffeeDiplom;
    }

    public Cashier() {
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public boolean isCoffeeDiplom() {
        return coffeeDiplom;
    }

    public void setCoffeeDiplom(boolean coffeeDiplom) {
        this.coffeeDiplom = coffeeDiplom;
    }
}
