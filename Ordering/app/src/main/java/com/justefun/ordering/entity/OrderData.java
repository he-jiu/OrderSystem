package com.justefun.ordering.entity;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.entity
 * @time 2018/12/16 21:14
 * @describe 订单数据实体
 */
public class OrderData {
    private String DishName;
    private String dishImg;
    private String DishID;

    private double Price;//单价
    private int number;//数目


    public String getDishName() {
        return DishName;
    }
    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishImg() {
        return dishImg;
    }

    public void setDishImg(String dishImg) {
        this.dishImg = dishImg;
    }

    public String getDishID() {
        return DishID;
    }
    public void setDishID(String dishID) {
        DishID = dishID;
    }

    public double getPrice() {
        return Price;
    }
    public void setPrice(double price) {
        Price = price;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
