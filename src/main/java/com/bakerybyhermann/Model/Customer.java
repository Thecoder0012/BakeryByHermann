package com.bakerybyhermann.Model;

public class Customer extends Person {

    private int repeatedVisits;
    private String companyName;

    public Customer(int personId, String firstName, String lastName, String address, int zipCode, String city, int phoneNumber, String email, int repeatedVisits, String companyName) {
        super(personId, firstName, lastName, address, zipCode, city, phoneNumber, email);
        this.repeatedVisits = repeatedVisits;
        this.companyName = companyName;
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
}
