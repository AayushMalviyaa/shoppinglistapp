package com.example.myapplication.model;

public class Item {
    private String itemname ;
    private int itemquantity;
    private int itemsize;
    private String itemcolour;
    private int id;
    private String dateitemadded;

    public Item() {
    }

    public Item(String itemname, int itemquantity, int itemsize, String itemcolour, int id, String dateitemadded) {
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.itemsize = itemsize;
        this.itemcolour = itemcolour;
        this.id = id;
        this.dateitemadded = dateitemadded;
    }
    public Item(String itemname, int itemquantity, int itemsize, String itemcolour, String dateitemadded) {
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.itemsize = itemsize;
        this.itemcolour = itemcolour;
        this.dateitemadded = dateitemadded;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(int itemquantity) {
        this.itemquantity = itemquantity;
    }

    public int getItemsize() {
        return itemsize;
    }

    public void setItemsize(int itemsize) {
        this.itemsize = itemsize;
    }

    public String getItemcolour() {
        return itemcolour;
    }

    public void setItemcolour(String itemcolour) {
        this.itemcolour = itemcolour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateitemadded() {
        return dateitemadded;
    }

    public void setDateitemadded(String dateitemadded) {
        this.dateitemadded = dateitemadded;
    }
}

