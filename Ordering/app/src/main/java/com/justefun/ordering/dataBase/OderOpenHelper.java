package com.justefun.ordering.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Justefun
 * @projectname: Ordering
 * @class name：com.justefun.ordering.dataBase
 * @time 2018/12/21 16:47
 * @describe 订单数据库初始化
 */
public class OderOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "orders.db";
    private static final String TABLE_NAME = "orders";


    public OderOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (DishName varchar(20) primary key,dishImg varchar(20),DishID Integer,Price Double,number Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
