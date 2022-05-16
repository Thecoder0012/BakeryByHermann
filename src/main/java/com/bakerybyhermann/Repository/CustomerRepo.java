package com.bakerybyhermann.Repository;


import com.bakerybyhermann.Model.Customer;
import com.zaxxer.hikari.SQLExceptionOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAll(){
        String sql = "SELECT customer_id as personId, first_name, last_name, CONCAT(street_name, ' ', street_number) as address, zip_code_tbl.zip_code, city, phone_number, e_mail as email, repeated_visits, company_name\n" +
                "   FROM customer_tbl \n" +
                "                JOIN address_tbl  ON  address_tbl.address_id = customer_tbl.address_id \n" +
                "                JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.query(sql,rowMapper);
    }

//    public int getAddressId(){
//        String sql = "SELECT customer_id, address_id\n" +
//                "   FROM customer_tbl \n" +
//                "                JOIN address_tbl  ON  address_tbl.address_id = customer_tbl.address_id \n" +
//                "                ";
//        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
//        List<Customer> cuscus =  jdbcTemplate.query(sql,rowMapper);
//        for (int i = 0; i < cuscus.size(); i++) {
//            System.out.println(cuscus.get(i).getAddress());
//        }
//        return 0;
//
//    }

    public int getAddressId(){
        int addressId = 0;
          final  String jdbc_driver = "COM.MYSQL.JDBC.DRIVER";
          final String DATABASE_URL = "jdbc:mysql://localhost:3306/bakerybyhermann";
          Connection con;

        try {
            con = DriverManager.getConnection(DATABASE_URL, "root", "KKE94utu");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT address_id FROM address_tbl ORDER BY address_id DESC LIMIT 1");
            if (rs != null){
                while (rs.next()){
                    addressId =rs.getInt("address_id");
                            System.out.println("Adress id: " + addressId);
                }
            }
            s.close();
            con.close();
        }catch (SQLException sqlException){
            System.out.println("SqlException: "+ sqlException.getMessage());
        }
        return addressId;
    }

    public void addNew (Customer customer) {
        String address = customer.getAddress(); //her bruger vi input fra Html
        String[] splitted = address.split(" "); // her splitter inputtet

        System.out.println(Arrays.toString(splitted)); // her debugger vi for at være sikre
        for (int i = 0; i < splitted.length; i++) {
            System.out.println(splitted[i]);
        }
        int streetNumber = Integer.parseInt(splitted[1]); // her har vi gadenummet
        String streetName = splitted[0]; // her her vi gadenavnet

        String sql2 = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";

        jdbcTemplate.update(sql2,streetName,streetNumber,customer.getZipCode());

        //her skal vi finde address id fra result set
        int addressId = getAddressId();
        System.out.println("the new address id: "+addressId); // debug purpose

        String sql = "INSERT INTO customer_tbl(first_name,last_name,address_id,phone_number,e_mail, repeated_visits, company_name)" +
                " VALUES (?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,customer.getFirstName(),customer.getLastName(),addressId,customer.getPhoneNumber(),
                customer.getEmail(),customer.getRepeatedVisits(),customer.getCompanyName());
    }

    public Customer findById(int id){
        String sql = "SELECT customer_id as personId, first_name, last_name, CONCAT(street_name, ' ', street_number) as address, zip_code_tbl.zip_code, city, phone_number, e_mail as email, repeated_visits, company_name\n" +
                "   FROM customer_tbl \n" +
                "                JOIN address_tbl  ON  address_tbl.address_id = customer_tbl.address_id \n" +
                "                JOIN zip_code_tbl ON address_tbl.zip_code = zip_code_tbl.zip_code WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void updateById(int id, Customer c){
        String address = c.getAddress(); //her bruger vi input fra Html
        String[] splitted = address.split(" "); // her splitter inputtet

        System.out.println(Arrays.toString(splitted)); // her debugger vi for at være sikre
        for (int i = 0; i < splitted.length; i++) {
            System.out.println(splitted[i]);
        }
        int streetNumber = Integer.parseInt(splitted[1]); // her har vi gadenummet
        String streetName = splitted[0]; // her her vi gadenavnet

        String sql1 = "UPDATE address_tbl SET street_name = ?, street_number = ?, zip_code = ? WHERE address_id = ?";
        jdbcTemplate.update(sql1, streetName, streetNumber, c.getZipCode(), getUpdateAddressId(id) );

        String sql = "UPDATE customer_tbl SET customer_id = ?, first_name = ?, last_name = ?, address_id = ?, phone_number = ?," +
                "e_mail = ?, repeated_visits = ?, company_name = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, c.getPersonId(), c.getFirstName(), c.getLastName(), getUpdateAddressId(id), c.getPhoneNumber(), c.getEmail(),
                c.getRepeatedVisits(), c.getCompanyName(), id);
    }

    public int getUpdateAddressId(int id){
        int addressId = 0;
        final  String jdbc_driver = "COM.MYSQL.JDBC.DRIVER";
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/bakerybyhermann";
        Connection con;

        try {
            con = DriverManager.getConnection(DATABASE_URL, "root", "KKE94utu");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT address_id FROM customer_tbl WHERE customer_id = " + id);
            if (rs != null){
                while (rs.next()){
                    addressId =rs.getInt("address_id");
                    System.out.println("Adress id: " + addressId);
                }
            }
            s.close();
            con.close();
        }catch (SQLException sqlException){
            System.out.println("SqlException: "+ sqlException.getMessage());
        }
        return addressId;
    }


    public void delete(int customerId){
        String sql = "DELETE customer_tbl,address_tbl FROM customer_tbl " +
                "INNER JOIN address_tbl ON customer_tbl.address_id = address_tbl.address_id WHERE customer_id = ?";
        jdbcTemplate.update(sql,customerId);
    }

}
