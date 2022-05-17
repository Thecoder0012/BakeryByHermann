package com.bakerybyhermann.Model;

public class Address {

    private int addressId;
    private String streetName;
    private int streetNumber;


    public Address(int addressId, String streetName, int streetNumber) {
        this.addressId = addressId;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }
}
