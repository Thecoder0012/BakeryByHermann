package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> fetchAll(){
        String sql = "SELECT * FROM product_tbl";
        RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void addNew (Product p){
        String sql = "INSERT INTO product_tbl(product_name, price) VALUES (?,?)";
        jdbcTemplate.update(sql, p.getProductName(), p.getPrice());
    }

    public Product findById(int id){
        String sql = "SELECT * FROM product_tbl WHERE product_id = ?";
        RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void updateById(int id, Product p){
        String sql = "UPDATE product_tbl SET product_name = ?, price = ? WHERE product_id = ?";
        jdbcTemplate.update(sql, p.getProductName(), p.getPrice(), id);
    }

    public void delteById(int id){
        String sql = "DELETE FROM product_tbl WHERE product_id = ?";
        jdbcTemplate.update(sql, id);

    }
}
