package com.bakerybyhermann.Model;

public class Department {

    private int departmentId;
    private String locationName;
    private String shortName;
    private Address address;

    public Department(int departmentId, String locationName, String shortName, Address address) {
        this.departmentId = departmentId;
        this.locationName = locationName;
        this.shortName = shortName;
        this.address = address;
    }

    public Department() {
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
