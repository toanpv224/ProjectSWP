/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dal.SubCategoryDAO;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductCategory {
    private int category_id;
    private String category_name, description;
    private int status;
    private List<SubCategory> subCategoryList;
    public ProductCategory() {
    }

    public ProductCategory(int category_id, String category_name, String description, int status) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.description = description;
        this.status = status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
        public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList() {
        SubCategoryDAO scd = new SubCategoryDAO();
        this.subCategoryList = scd.getSubCategoryByCategoryId(category_id);
    }
}
