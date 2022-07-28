/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class PostSubCategory {
    private int id;
    private PostCategory category;
    private String name;
    private String Description;
    private boolean feature;

    public PostSubCategory() {
    }

    public PostSubCategory(int id, String name, String Description, boolean feature) {
        this.id = id;
        this.name = name;
        this.Description = Description;
        this.feature = feature;
    }
    
    public PostSubCategory(int id, PostCategory category, String name, String Description, boolean feature) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.Description = Description;
        this.feature = feature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PostCategory getCategory() {
        return category;
    }

    public void setCategory(PostCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public boolean isFeature() {
        return feature;
    }

    public void setFeature(boolean feature) {
        this.feature = feature;
    }


    
}
