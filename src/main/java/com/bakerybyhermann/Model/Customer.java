package com.bakerybyhermann.Model;

public class Customer extends Person {

    private int customerId;
    private int repeatedVisits;
    private String companyName;

    public Customer(int personId, String firstName, String lastName, Address address, int phoneNumber, String email, int customerId, String companyName) {
        super(personId, firstName, lastName, address, phoneNumber, email);
        this.customerId = customerId;
        this.repeatedVisits = repeatedVisits;
        this.companyName = companyName;
    }

    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRepeatedVisits() {
        return repeatedVisits;
    }

    public void setRepeatedVisits(int repeatedVisits) {
        this.repeatedVisits = repeatedVisits;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}