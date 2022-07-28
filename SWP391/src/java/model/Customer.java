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
public class Customer extends Account{

    public Customer() {
    }

    public Customer(int user_id, String full_name, boolean gender, String email, String phone, boolean feature) {
        super(user_id, full_name, gender, email, phone, feature);
    }

    public Customer(String username, String password, String full_name, int role_id, boolean gender, String email, String city, String country, String address, String phone, String image_url, boolean feature, String myHash, int active) {
        super(username, password, full_name, role_id, gender, email, city, country, address, phone, image_url, feature, myHash, active);
    }

    
       
    
}
