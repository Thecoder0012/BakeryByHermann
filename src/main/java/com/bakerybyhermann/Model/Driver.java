package com.bakerybyhermann.Model;

public class Driver extends Employee {

    private int driverId;
    private String driverLicenseNumber;
    private String registrationNumber;

    public Driver(int personId, String firstName, String lastName, Address address, int phoneNumber, String email, int employeeId, int age, boolean gender, boolean fullTimeEmployee, int driverId, String driverLicenseNumber, String registrationNumber) {
        super(personId, firstName, lastName, address, phoneNumber, email, employeeId, age, gender, fullTimeEmployee);
        this.driverId = driverId;
        this.driverLicenseNumber = driverLicenseNumber;
        this.registrationNumber = registrationNumber;
    }

    public Driver() {
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
