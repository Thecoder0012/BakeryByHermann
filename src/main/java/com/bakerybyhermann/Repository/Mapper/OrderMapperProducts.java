package com.bakerybyhermann.Repository.Mapper;

import com.bakerybyhermann.Model.Product;
import com.bakerybyhermann.Model.ProductList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapperProducts implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product();

        ProductList productInList = new ProductList();

        product.setProductId(rs.getInt("pId"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getInt("price"));

        productInList.setProduct(product);
        productInList.setQuantity(rs.getInt("quantity"));

        return productInList;
    }
}
