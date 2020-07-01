package com.justefun.ordering.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.justefun.ordering.entity.OrderData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.dataBase
 * @time 2018/12/21 17:15
 * @describe order数据库操作类
 */
public class DataOperation {
    private final OderOpenHelper oderOpenHelper;
    private SQLiteDatabase db;

    public DataOperation(Context context) {
        oderOpenHelper = new OderOpenHelper(context);
        db = oderOpenHelper.getWritableDatabase();
    }

    // 查询所有的订单信息
    public List<OrderData> queryMany() {
        ArrayList<OrderData> datas = new ArrayList<OrderData>();
        Cursor c = db.rawQuery("select * from orders", null);
        while (c.moveToNext()) {
            OrderData data = new OrderData();
            data.setDishName(c.getString(0));
            data.setDishImg(c.getString(1));
            data.setDishID(c.getString(2));
            data.setPrice(c.getDouble(3));
            data.setNumber(c.getInt(4));
            datas.add(data);
        }
        c.close();
        return datas;
    }
    //查询当前菜品是否存在
    public boolean CheckIsDataAlreadyIn(String value) {

        String Query = "Select * from orders where DishName =?";
        Cursor cursor = db.rawQuery(Query, new String[] { value });
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //查询当前菜品
    public OrderData getItem(String value){
        String Query = "Select * from orders where DishName =?";
        Cursor c = db.rawQuery(Query, new String[] { value });
        OrderData data = new OrderData();
        if (c.moveToFirst()) {
            data.setDishName(c.getString(0));
            data.setDishImg(c.getString(1));
            data.setDishID(c.getString(2));
            data.setPrice(c.getDouble(3));
            data.setNumber(c.getInt(4));
        }
        return data;
    }
    //查询总价
    public double getSum(){
        String Query = "Select SUM(Price*number) from orders";
        Cursor c = db.rawQuery(Query,null);
        double sum=0;
        if (c.moveToFirst()) {
            sum =c.getDouble(0);
        }
        return sum;
    }

    //添加订单信息
    public void add(OrderData data){
        db.execSQL("insert into orders values(?,?,?,?,?)",new Object[]{data.getDishName(),data.getDishImg(),data.getDishID(),data.getPrice(),data.getNumber()});
    }
    //修改订单数量
    public void update(OrderData data){
        db.execSQL("update orders set number=? where DishName=?",new Object[]{data.getNumber(),data.getDishName()});
    }
    //删除一条订单
    public void delete(OrderData data){
        db.execSQL("delete from orders where DishName=?",new Object[]{data.getDishName()});
    }
    //清空订单
    public void clear(){
        db.execSQL("delete from orders");
    }

}
