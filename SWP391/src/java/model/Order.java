/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author win
 */
public class Order {

    private int order_id;
    private int user_id;
    private String order_Date;
    private String require_date;
    private String shipped_Date;
    private int ship_via;
    private double freight;
    private String ship_name;
    private String ship_address;
    private boolean ship_gender;
    private String ship_mobile;
    private String ship_email;
    private String ship_city;
    private int status;
    private String note;
    private String payment;
    private double total_price;
    private String sale_note;
    
    private List<OrderDetail> orderDetailList;
    private Date orderDate;
    private Account account;
    private Account sale;
    private OrderStatus orderStatus;
    public Order() {
    }

    public Order(int order_id, int user_id, String order_Date, String require_date, String shipped_Date, int ship_via, double freight, String ship_name, String ship_address, boolean ship_gender, String ship_mobile, String ship_email, String ship_city, int status, String note, String payment, double total_price) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_Date = order_Date;
        this.require_date = require_date;
        this.shipped_Date = shipped_Date;
        this.ship_via = ship_via;
        this.freight = freight;
        this.ship_name = ship_name;
        this.ship_address = ship_address;
        this.ship_gender = ship_gender;
        this.ship_mobile = ship_mobile;
        this.ship_email = ship_email;
        this.ship_city = ship_city;
        this.status = status;
        this.note = note;
        this.payment = payment;
        this.total_price = total_price;
    }

    public Order(int order_id, int user_id, String order_Date, String require_date, String shipped_Date, int ship_via, double freight, String ship_name, String ship_address, boolean ship_gender, String ship_mobile, String ship_email, String ship_city, int status, String note, String payment, double total_price, String sale_note) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_Date = order_Date;
        this.require_date = require_date;
        this.shipped_Date = shipped_Date;
        this.ship_via = ship_via;
        this.freight = freight;
        this.ship_name = ship_name;
        this.ship_address = ship_address;
        this.ship_gender = ship_gender;
        this.ship_mobile = ship_mobile;
        this.ship_email = ship_email;
        this.ship_city = ship_city;
        this.status = status;
        this.note = note;
        this.payment = payment;
        this.total_price = total_price;
        this.sale_note = sale_note;
    }
    
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_Date() {
        return order_Date;
    }

    public void setOrder_Date(String order_Date) {
        this.order_Date = order_Date;
    }

    public String getRequire_date() {
        return require_date;
    }

    public void setRequire_date(String require_date) {
        this.require_date = require_date;
    }

    public String getShipped_Date() {
        return shipped_Date;
    }

    public void setShipped_Date(String shipped_Date) {
        this.shipped_Date = shipped_Date;
    }

    public int getShip_via() {
        return ship_via;
    }

    public void setShip_via(int ship_via) {
        this.ship_via = ship_via;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public String getShip_address() {
        return ship_address;
    }

    public void setShip_address(String ship_address) {
        this.ship_address = ship_address;
    }

    public boolean isShip_gender() {
        return ship_gender;
    }

    public void setShip_gender(boolean ship_gender) {
        this.ship_gender = ship_gender;
    }

    public String getShip_mobile() {
        return ship_mobile;
    }

    public void setShip_mobile(String ship_mobile) {
        this.ship_mobile = ship_mobile;
    }

    public String getShip_email() {
        return ship_email;
    }

    public void setShip_email(String ship_email) {
        this.ship_email = ship_email;
    }

    public String getShip_city() {
        return ship_city;
    }

    public void setShip_city(String ship_city) {
        this.ship_city = ship_city;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.orderDate = OrderDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getSale() {
        return sale;
    }

    public void setSale(Account sale) {
        this.sale = sale;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSale_note() {
        return sale_note;
    }

    public void setSale_note(String sale_note) {
        this.sale_note = sale_note;
    }
    
    @Override
    public String toString() {
        return "Order{" + "order_id=" + order_id + ", user_id=" + user_id + ", order_Date=" + order_Date + ", require_date=" + require_date + ", shipped_Date=" + shipped_Date + ", ship_via=" + ship_via + ", freight=" + freight + ", ship_name=" + ship_name + ", ship_address=" + ship_address + ", ship_gender=" + ship_gender + ", ship_mobile=" + ship_mobile + ", ship_email=" + ship_email + ", ship_city=" + ship_city + ", status=" + status + ", note=" + note + ", payment=" + payment + ", total_price=" + total_price + '}';
    }

}
