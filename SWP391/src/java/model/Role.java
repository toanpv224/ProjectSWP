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
public class Role {
    private int rId;
    private String rName;

    public Role() {
    }

    public Role(int rId, String rName) {
        this.rId = rId;
        this.rName = rName;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }
    
    
}
