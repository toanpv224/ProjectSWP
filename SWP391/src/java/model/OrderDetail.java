/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author win
 */
public class OrderDetail {
    private int order_id;
    private Product product;
    private int quantity;
    private double price;
    private ProductCategory category;

    public OrderDetail(int order_id, Product product_id, int quantity, double price) {
        this.order_id = order_id;
        this.product = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "OrderDetail{" + "order_id=" + order_id + ", product_id=" + product + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
    
}
