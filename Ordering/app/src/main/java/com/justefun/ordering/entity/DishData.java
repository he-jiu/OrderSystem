package com.justefun.ordering.entity;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.entity
 * @time 2018/12/16 21:15
 * @describe 菜品实体类
 */
public class DishData {
    private String dishImg;
    private String dishName="默认";
    private String dishID;
    private String dishClass="默认";
    private double dishPrice=0;//价格
    private String introduce="默认";

    public String getDishImg() {
        return dishImg;
    }

    public void setDishImg(String dishImg) {
        this.dishImg = dishImg;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
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
