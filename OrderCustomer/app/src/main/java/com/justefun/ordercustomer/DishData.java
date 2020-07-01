package com.justefun.ordercustomer;

public class DishData {
    private String dishName="默认";
    private int dishID=0;
    private String dishClass="默认";
    private double dishPrice=0;
    private String introduce="默认";

    private int orderID=0;
    private int dishNum=0;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishID() {
        return dishID;
    }
    public int getDishNum() {
        return dishNum;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public void setDishNum(int dishNum) {
        this.dishNum = dishNum;
    }


    public String getDishClass() {
        return dishClass;
    }

    public void setDishClass(String dishClass) {
        this.dishClass = dishClass;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
