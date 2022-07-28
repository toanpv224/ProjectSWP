/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Product {
    private int product_id;
    private String name;
    private String model;
    private String thumbnail;
    private String briefInfor;
    private List<ProductImage> images;
    private SubCategory subCategory;
    private int unit_in_stock;
    private String updated_date;
    private double original_price;
    private double sale_price;
    private String product_details;
    private List<Feedback> feedbacks;
    private boolean featured;
    private int status;

    public Product() {
    }

    public Product(int product_id, String name, String model, String thumbnail, String briefInfor, SubCategory subCategory, int unit_in_stock, double original_price, double sale_price, int status) {
        this.product_id = product_id;
        this.name = name;
        this.model = model;
        this.thumbnail = thumbnail;
        this.briefInfor = briefInfor;
        this.subCategory = subCategory;
        this.unit_in_stock = unit_in_stock;
        this.original_price = original_price;
        this.sale_price = sale_price;
        this.status = status;
    }

    public Product(int product_id, String name, String model, String thumbnail, String briefInfor, List<ProductImage> images, SubCategory subCategory, int unit_in_stock, String updated_date, double original_price, double sale_price, String product_details, List<Feedback> feedbacks, boolean featured, int status) {
        this.product_id = product_id;
        this.name = name;
        this.model = model;
        this.thumbnail = thumbnail;
        this.briefInfor = briefInfor;
        this.images = images;
        this.subCategory = subCategory;
        this.unit_in_stock = unit_in_stock;
        this.updated_date = updated_date;
        this.original_price = original_price;
        this.sale_price = sale_price;
        this.product_details = product_details;
        this.feedbacks = feedbacks;
        this.featured = featured;
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBriefInfor() {
        return briefInfor;
    }

    public void setBriefInfor(String briefInfor) {
        this.briefInfor = briefInfor;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public int getUnit_in_stock() {
        return unit_in_stock;
    }

    public void setUnit_in_stock(int unit_in_stock) {
        this.unit_in_stock = unit_in_stock;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
