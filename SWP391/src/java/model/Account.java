/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author tretr
 */
public class Account {

    private int user_id;
    private String username;
    private String password;
    private String full_name;
    private int role_id;
    private boolean gender;
    private String email;
    private String city;
    private String country;
    private String address;
    private String phone;
    private String image_url;
    private boolean feature;
    private String myHash;
    private int active;
    private Role role;
    
    public Account() {
    }

    public Account(String username, String password, String full_name, int role_id, boolean gender, String email, String city, String country, String address, String phone, boolean feature, String myHash, int active) {
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.role_id = role_id;
        this.gender = gender;
        this.email = email;
        this.city = city;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.feature = feature;
        this.myHash = myHash;
        this.active = active;
    }

    public Account(int user_id, String username, String password, String full_name, int role_id, boolean gender, String email, String city, String country, String address, String phone, String image_url, boolean feature) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.role_id = role_id;
        this.gender = gender;
        this.email = email;
        this.city = city;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.image_url = image_url;
        this.feature = feature;
    }

    public Account(String username, String password, String full_name, int role_id, boolean gender, String email, String city, String country, String address, String phone, String image_url, boolean feature, String myHash, int active) {
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.role_id = role_id;
        this.gender = gender;
        this.email = email;
        this.city = city;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.image_url = image_url;
        this.feature = feature;
        this.myHash = myHash;
        this.active = active;
    }

    
    public Account(int user_id, String username, String password, String full_name, int role_id, boolean gender, String email, String city, String country, String address, String phone, String image_url, boolean feature, String myHash, int active) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.role_id = role_id;
        this.gender = gender;
        this.email = email;
        this.city = city;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.image_url = image_url;
        this.feature = feature;
        this.myHash = myHash;
        this.active = active;
    }

    public Account(int user_id, String full_name, boolean gender, String email, String phone, boolean feature) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.feature = feature;
    }

 
    
    public Account(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getUsername() {
        return username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isFeature() {
        return feature;
    }

    public void setFeature(boolean feature) {
        this.feature = feature;
    }

    public String getMyHash() {
        return myHash;
    }

    public void setMyHash(String myHash) {
        this.myHash = myHash;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Account{" + "user_id=" + user_id + ", username=" + username + ", password=" + password + ", full_name=" + full_name + ", role_id=" + role_id + ", gender=" + gender + ", email=" + email + ", city=" + city + ", country=" + country + ", address=" + address + ", phone=" + phone + ", image_url=" + image_url + ", feature=" + feature + ", myHash=" + myHash + ", active=" + active + ", role=" + role + '}';
    }

}
