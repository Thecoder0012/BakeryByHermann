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
import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAll(){
        String sql = "SELECT customer_id, first_name, last_name, CONCAT(street_name, ' ', street_number) as address, zip_code_tbl.zip_code, city, phone_number, e_mail as email, repeated_visits, company_name\n" +
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
            con = DriverManager.getConnection(DATABASE_URL, "root", "Mohammad90%");
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
        String sql2 = "INSERT INTO address_tbl(street_name, street_number, zip_code) VALUES (?,?,?)";

        jdbcTemplate.update(sql2,customer.getAddress()
                ,3,customer.getZipCode());

        //her skal vi finde address id fra result set
        int addressId = getAddressId();
        System.out.println("the new address id: "+addressId);

        String sql = "INSERT INTO customer_tbl(first_name,last_name,address_id,phone_number,e_mail, repeated_visits, company_name)" +
                " VALUES (?,?,?,?,?,?,?)";



        jdbcTemplate.update(sql,customer.getFirstName(),customer.getLastName(),addressId,customer.getPhoneNumber(),
                customer.getEmail(),customer.getRepeatedVisits(),customer.getCompanyName());


    }

    public void delete(int customerId){
        String sql = "DELETE customer_tbl,address_tbl FROM customer_tbl " +
                "INNER JOIN address_tbl ON customer_tbl.address_id = address_tbl.address_id WHERE customer_id = ?";
        jdbcTemplate.update(sql,customerId);
    }

}
