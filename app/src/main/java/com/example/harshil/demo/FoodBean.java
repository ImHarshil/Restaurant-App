/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.harshil.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikhil
 */
public class FoodBean {
    protected int fid;
    protected String fname;
    protected String fcategory;
    protected int fprice;
    protected int fdiscount;
    protected String fdescription;
    protected String ftype;

    public String getFood(String name){
        return "select * from foodmaster where fname like '%"+name+"%'";
    }
    public String getFood(int fid){
        return  "select * from foodmaster where fid = "+fid+"";
    }
    
    public static String getFoodByCategory(String name) {
        return "select * from foodmaster where fcategory ='"+name+ "'";
    }
    /**
     * @return the fid
     */
    public int getFid() {
        return fid;
    }

    /**
     * @param fid the fid to set
     */
    public void setFid(int fid) {
        this.fid = fid;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the fcategory
     */
    public String getFcategory() {
        return fcategory;
    }

    /**
     * @param fcategory the fcategory to set
     */
    public void setFcategory(String fcategory) {
        this.fcategory = fcategory;
    }

    /**
     * @return the fprice
     */
    public int getFprice() {
        return fprice;
    }

    /**
     * @param fprice the fprice to set
     */
    public void setFprice(int fprice) {
        this.fprice = fprice;
    }

    /**
     * @return the fdiscount
     */
    public int getFdiscount() {
        return fdiscount;
    }

    /**
     * @param fdiscount the fdiscount to set
     */
    public void setFdiscount(int fdiscount) {
        this.fdiscount = fdiscount;
    }

    /**
     * @return the fdescription
     */
    public String getFdescription() {
        return fdescription;
    }

    /**
     * @param fdescription the fdescription to set
     */
    public void setFdescription(String fdescription) {
        this.fdescription = fdescription;
    }

    /**
     * @return the ftype
     */
    public String getFtype() {
        return ftype;
    }

    /**
     * @param ftype the ftype to set
     */
    public void setFtype(String ftype) {
        this.ftype = ftype;
    }
}
