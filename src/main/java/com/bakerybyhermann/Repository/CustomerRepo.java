package com.bakerybyhermann.Repository;


import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Person;
import com.bakerybyhermann.Repository.Mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAll() {
//        String sql = "SELECT customer_tbl.customer_id as personId, first_name, last_name,street_name as streetName,street_number as streetNumber, zip_code_tbl.zip_code, city, phone_number, e_mail as email, customer_tbl.repeated_visits, customer_tbl.company_name" +
//                "   FROM person_tbl JOIN customer_tbl ON person_tbl.person_id = customer_tbl.customer_id " +
//                "                JOIN address_tbl  ON  address_tbl.address_id = customer_tbl.customer_id" +
//                "                JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";

//        String sql1 = "SELECT * FROM address_tbl";
        String sql = "SELECT customer_id as personId,first_name,last_name," + "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,repeated_visits,company_name\n" +
                "FROM person_tbl\n" +
                "INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";

        //disse skal i object for sig knyttet til denne person object


//       // String sql = "SELECT customer_tbl.customer_id as personId,first_name,last_name,CONCAT(street_name, ' ', street_number) as address,address_tbl.zip_code,city,phone_number,e_mail as email,repeated_visits,company_name\n" + "FROM person_tbl\n" + "INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id\n" + "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" + "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";
//        String sql = "SELECT * FROM person_tbl " +
//                "     INNER JOIN customer_tbl " +
//                "     ON person_tbl.person_id = customer_tbl.customer_id" +
//                "       ";

        RowMapper rowMapper = new CustomerMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }


    public int getAddressId() {
        String sql = "SELECT address_id,street_name,street_number," +
                "zip_code_tbl.zip_code, zip_code_tbl.city FROM address_tbl,  zip_code_tbl ORDER BY address_id DESC LIMIT 1";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper);
        return address.getAddressId();
    }

    public int getPersonId() {
        String sql = "SELECT * FROM person_tbl ORDER BY person_id DESC LIMIT 1";
        RowMapper<Person> rowMapper = new BeanPropertyRowMapper<>(Person.class);
        Person person = jdbcTemplate.queryForObject(sql, rowMapper);
        return person.getPersonId();
    }

    public String getPersonCity(int zip){
        String sql = "SELECT address_id,street_name,street_number,\n" +
                "                zip_code_tbl.zip_code, zip_code_tbl.city \n" +
                "                FROM address_tbl,  zip_code_tbl ORDER BY address_id DESC LIMIT 1";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper);
        return address.getCity();
    }

    public void addNew(Customer customer, Address address) {
////        String address = customer.getAddress(); //her bruger vi input fra Html
//        String[] splitted = address.split(" "); // her splitter inputtet
//
//        int streetNumber = Integer.parseInt(splitted[1]); // her har vi gadenummet
//        String streetName = splitted[0]; // her her vi gadenavnet


//        jdbcTemplate.update(sql2,streetName,streetNumber,customer.getZipCode());

        //her skal vi finde address id fra result set


        System.out.println("Street number is: "+address.getStreetNumber());
        System.out.println("Street number: "+ customer.getAddress().getStreetNumber());
        String sql = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";
        jdbcTemplate.update(sql, address.getStreetName(), address.getStreetNumber(), address.getZipCode());

//        int addressId = getAddressId();
//        System.out.println("the new address id: "+addressId); // debug purpose

        //set city

        String sql2 = "INSERT INTO person_tbl(first_name,last_name,address_id,phone_number,e_mail) VALUES (?,?,?,?,?)";
        System.out.println("First name: "+customer.getFirstName());
        jdbcTemplate.update(sql2, customer.getFirstName(), customer.getLastName(), getAddressId(), customer.getPhoneNumber(),
                customer.getEmail());

        String sql1 = "INSERT INTO customer_tbl(person_id, repeated_visits, company_name)" + " VALUES (?,?,?)";

        System.out.println("Company name: "+customer.getCompanyName());
        System.out.println("City is: "+ customer.getAddress().getZipCode());
        customer.getAddress().setCity(getPersonCity(address.getZipCode()));
        System.out.println("City is: "+ customer.getAddress().getCity());
        jdbcTemplate.update(sql1, getPersonId(), customer.getRepeatedVisits(), customer.getCompanyName());


    }



    public void updateById(int id, Customer c) {
        /*String address = c.getAddress(); //her bruger vi input fra Html
        String[] splitted = address.split(" "); // her splitter inputtet

        int streetNumber = Integer.parseInt(splitted[1]); // her har vi gadenummet
        String streetName = splitted[0]; // her her vi gadenavnet*/


        String sqlAddress = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
//        System.out.println(c.getAddress().getStreetName());
        System.out.println("id is 45 " +c.getAddress().getAddressId());
        System.out.println("streetname must be : afk " +c.getAddress().getStreetName());



        jdbcTemplate.update(sqlAddress, c.getAddress().getStreetName(), c.getAddress().getStreetNumber(), c.getAddress().getZipCode(), 44);
        System.out.println(c.getAddress().getAddressId());
        System.out.println("til her");
        String sqlCustomer = "UPDATE customer_tbl SET repeated_visits = ?, company_name = ? WHERE customer_id = ?";
        jdbcTemplate.update(sqlCustomer, c.getRepeatedVisits(), c.getCompanyName(), id);

        System.out.println("før"+id);

        String sqlPerson = "UPDATE person_tbl SET first_name = ?, last_name = ?, phone_number = ?,e_mail = ? WHERE person_tbl.person_id = ?";
        jdbcTemplate.update(sqlPerson, c.getFirstName(), c.getLastName(), c.getPhoneNumber(),c.getEmail(), id);
        System.out.println("efter"+id);

        System.out.println("id isssss 45 " +getUpdateAddressId(id));









    }

    public int getUpdateAddressId(int id) {
        String sql = "SELECT person_tbl.address_id, street_name,street_number,zip_code_tbl.zip_code,city FROM person_tbl \n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id \n" +
                "INNER JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code\n" +
                "WHERE person_id = ?";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address address = jdbcTemplate.queryForObject(sql, rowMapper, id);
        System.out.println("new debug get object address id"+address.getAddressId());
        return address.getAddressId();
    }

    public Customer findById(int id) {
//        String sql = "SELECT customer_id as personId, first_name, last_name, " +
//                "street_name as streetName,street_number as streetNumber, " +
//                "address_tbl.zip_code, phone_number, e_mail as email, repeated_visits, company_name\n" + "  " +
//                " FROM person_tbl \n" + "  " +
//                " INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id \n" + " " +
//                " INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id WHERE customer_id = ?";
//

        String sql = "SELECT customer_id as personId,first_name,last_name," + "street_name as streetName,street_number as streetNumber,address_tbl.zip_code,city," +
                "phone_number,e_mail as email,repeated_visits,company_name\n" +
                "FROM person_tbl\n" +
                "INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id\n" +
                "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" +
                "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code WHERE customer_id = ?";

        //disse skal i object for sig knyttet til denne person object


//       // String sql = "SELECT customer_tbl.customer_id as personId,first_name,last_name,CONCAT(street_name, ' ', street_number) as address,address_tbl.zip_code,city,phone_number,e_mail as email,repeated_visits,company_name\n" + "FROM person_tbl\n" + "INNER JOIN customer_tbl ON person_tbl.person_id = customer_tbl.person_id\n" + "INNER JOIN address_tbl ON person_tbl.address_id = address_tbl.address_id\n" + "INNER JOIN  zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";
//        String sql = "SELECT * FROM person_tbl " +
//                "     INNER JOIN customer_tbl " +
//                "     ON person_tbl.person_id = customer_tbl.customer_id" +
//                "       ";
        RowMapper rowMapper = new CustomerMapper();

        List<Customer> customerList = jdbcTemplate.query(sql,rowMapper,id);

        return customerList.get(0);
    }

    public void delete(int customerId) {
        String sql = "DELETE customer_tbl,person_tbl,address_tbl FROM customer_tbl \n" +
                "INNER JOIN person_tbl ON customer_tbl.person_id = person_tbl.person_id \n" +
                "INNER JOIN address_tbl on person_tbl.address_id = address_tbl.address_id\n" +
                "WHERE customer_id = ?";



        jdbcTemplate.update(sql, customerId);
    }

}
