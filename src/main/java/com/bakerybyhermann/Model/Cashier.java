package com.bakerybyhermann.Model;

import java.util.List;

public class Cashier extends Employee{

        private List<String> languages;
        private boolean isOver18;

        public Cashier(int personId, String firstName, String lastName, String address, int zipCode, String city, int phoneNumber, String email, int repeatedVisits, String companyName, int age, boolean gender, List<String> languages) {
                super(personId, firstName, lastName, address, zipCode, city, phoneNumber, email, repeatedVisits, companyName, age, gender);
                this.languages = languages;
        }

        public Cashier() {
        }

        public List<String> getLanguages() {
                return languages;
        }

        public void setLanguages(List<String> languages) {
                this.languages = languages;
        }

        public boolean isOver18() {
                return isOver18;
        }

        public void setOver18(boolean over18) {
                isOver18 = over18;
        }
}
