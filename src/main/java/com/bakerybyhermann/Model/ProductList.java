package com.bakerybyhermann.Model;

public class ProductList {

    private Product product;
    private int quantity;

    public ProductList(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public ProductList() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
