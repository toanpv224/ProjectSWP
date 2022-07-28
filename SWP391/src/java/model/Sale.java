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
public class Sale {

    private Account account;
    private List<Order> orderList;

    public Sale() {
    }

    public Sale(Account account, List<Order> orderList) {
        this.account = account;
        this.orderList = orderList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public double getRevenue() {
        double revenue = 0;
        for (int i = 0; i < orderList.size(); i++) {
            revenue += orderList.get(i).getTotal_price();
        }
        return revenue;
    }
    
    public static void main(String[] args) {
    }
}
