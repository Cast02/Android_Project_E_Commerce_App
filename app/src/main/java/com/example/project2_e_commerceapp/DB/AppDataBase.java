package com.example.project2_e_commerceapp.DB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2_e_commerceapp.AppLog;
import com.example.project2_e_commerceapp.Orders;
import com.example.project2_e_commerceapp.ShoppingCart;
import com.example.project2_e_commerceapp.User;

@Database(entities = {AppLog.class, User.class, ShoppingCart.class, Orders.class}, version = 6)
public abstract class AppDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppLog.db";
    public static final String APPLOG_TABLE = "applog_table";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String CART_TABLE = "CART_TABLE";
    public static final String ORDERS_TABLE = "ORDERS_TABLE";

    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();

    public abstract AppLogDAO AppLogDAO();

    public static  AppDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
