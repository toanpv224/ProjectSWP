/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Duy Phuong
 */
public class SubCategory {
    private int id;
    private String name;
    private ProductCategory category;
    private String descripton;
    private boolean featured;

    public SubCategory() {
    }

    public SubCategory(int id, String name, ProductCategory category, String descripton, boolean featured) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.descripton = descripton;
        this.featured = featured;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
    
    
}
