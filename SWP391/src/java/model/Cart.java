/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author win
 */
public class Cart {
    private List<Item> items; //contain items selected

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(String txt, List<Product> list) {
        items = new ArrayList<>();
        try {

            if (txt != null && txt.length() != 0) {   //exist cart
                String[] item = txt.split(","); //item split by , in cookie
                for (String i : item) {
                    String[] atr = i.split(":");  // 2 attribute of a item split by ; in cookie
                    int id = Integer.parseInt(atr[0]);
                    int quantity = Integer.parseInt(atr[1]);
                    Product p = getProductById(id, list);
                    Item t = new Item(p, quantity, p.getSale_price());
                    addItem(t);//add item to cart
                }

            }
        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public Cart(String txt, List<Product> list, int user_id) {
        items = new ArrayList<>();
        try {
            if (!txt.isEmpty()) {

                String[] splitField = txt.split("<"); //split cookie into cart
                List<String> listCart = new ArrayList<>();
                for (String splitField1 : splitField) {//split each cart into id>item:quantity
                    String[] field = splitField1.split(">");
                    for (String field1 : field) { //add each cart conresspond id into id and cart detail
                        listCart.add(field1);
                    }
                }
                for (int i = 0; i < listCart.size(); i++) {
                    if (i % 2 == 1) {//contain user_id 
                        if (Integer.parseInt(listCart.get(i)) == user_id) {// cart match user_id conresponding
                            String cartDetail = listCart.get(i + 1);//content cart detail user conrestponding

                            if (cartDetail != null && cartDetail.length() != 0) {   //exist cart
                                String[] item = cartDetail.split(","); //item split by , in cookie
                                for (String it : item) {
                                    String[] atr = it.split(":");  // 2 attribute of a item split by ; in cookie
                                    int id = Integer.parseInt(atr[0]);
                                    int quantity = Integer.parseInt(atr[1]);
                                    Product p = getProductById(id, list);
                                    double itemPrice;
                                    if (p.getSale_price() == 0) { //not have sale price
                                        itemPrice = p.getOriginal_price();
                                    } else {
                                        itemPrice = p.getSale_price();
                                    }
                                    Item t = new Item(p, quantity, itemPrice);
                                    addItem(t);//add item to cart
                                }

                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*return cart*/
    public List<Item> getItems() {
        return items;
    }

    /*return quantity of a item in cart*/
    public int getQuantityById(int id) {
        int quantiy = getItemById(id).getQuantity();
        return quantiy;
    }

    /*find item matched by id in list*/
    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.getProduct().getProduct_id() == id) {//product matched by id in list)
                return item;//return item if found
            }
        }

        return null;
    }

    public void addItem(Item t) {
        try {
            if (getItemById(t.getProduct().getProduct_id()) != null) {
                Item item = getItemById(t.getProduct().getProduct_id());
                int oldQuantity = item.getQuantity();
                item.setQuantity(oldQuantity + t.getQuantity());
            } else {
                items.add(t);//add new item incart
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*Remove a item*/
    public void removeItem(int id) {
        Item item = getItemById(id);
        items.remove(item);

    }

    /*get total money of cart*/
    public double getTotalMoney() {
        double total = 0;
        for (Item item : items) {
            total += (item.getQuantity() * item.getPrice());
        }
        return total;
    }

    /*Get product by id*/
    private Product getProductById(int id, List<Product> list) {
        for (Product product : list) {
            if (product.getProduct_id() == id) {    //found product
                return product;
            }
        }
        return null;  //can't found product
    }

    /*get shipping fee of cart */
    public double getFreight() {
        double totalMoney = getTotalMoney();
        if (totalMoney == 0 || totalMoney >= 1000000) { //order price >1.000.000
            return 0;
        }
        return 100000;
    }

    /*Remove a item*/
//    public void removeItem(int id) {
//        Item item = getItemById(id);
//        items.remove(item);
//
//    }
//
//    /*get total money of cart*/
//    public double getTotalMoney() {
//        double total = 0;
//        for (Item item : items) {
//            total += (item.getQuantity() * item.getPrice());
//        }
//       return total;
//    }
//
//    /*Get product by id*/
//    private Product getProductById(int id, List<Product> list) {
//        for (Product product : list) {
//            if (product.getProduct_id() == id) {    //found product
//                return product;
//            }
//        }
//        return null;  //can't found product
//    }
//    /*get shipping fee of cart */
//    public double getFreight(){
//        double totalMoney = getTotalMoney();
//        if(totalMoney==0||totalMoney>=1000000){ //order price >1.000.000
//            return 0;
//        }
//        return 100000;
//    }
}
