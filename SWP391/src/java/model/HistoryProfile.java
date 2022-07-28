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
public class HistoryProfile {

    private UpdateDate update;
    private String full_name;
    private boolean gender;
    private String email;
    private String address;
    private String phone;
    private boolean feature;
    private String update_by;

    public HistoryProfile() {
    }

    public HistoryProfile(UpdateDate update, String full_name, boolean gender, String email, String address, String phone, boolean feature, String update_by) {
        this.update = update;
        this.full_name = full_name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.feature = feature;
        this.update_by = update_by;
    }

    public UpdateDate getUpdate() {
        return update;
    }

    public void setUpdate(UpdateDate update) {
        this.update = update;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFeature() {
        return feature;
    }

    public void setFeature(boolean feature) {
        this.feature = feature;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    @Override
    public String toString() {
        return "HistoryProfile{" + "update=" + update.getDate() + ", full_name=" + full_name + ", gender=" + gender + ", email=" + email + ", address=" + address + ", phone=" + phone + ", feature=" + feature + ", update_by=" + update_by + '}';
    }
    
}
