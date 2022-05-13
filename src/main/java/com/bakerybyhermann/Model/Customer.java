package com.bakerybyhermann.Model;

public class Customer extends Person {

    private int repeatedVisits;
    private String companyName;
    private int customerId;

    public Customer(int personId, String firstName, String lastName, String address, int zipCode, String city, int phoneNumber, String email, int repeatedVisits, String companyName, int customerId) {
        super(personId, firstName, lastName, address, zipCode, city, phoneNumber, email);
        this.repeatedVisits = repeatedVisits;
        this.companyName = companyName;
        this.customerId = customerId;
    }

    public Customer() {
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
