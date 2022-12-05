package com.example.project2_e_commerceapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_e_commerceapp.AppLog;
import com.example.project2_e_commerceapp.Orders;
import com.example.project2_e_commerceapp.ShoppingCart;
import com.example.project2_e_commerceapp.User;

import java.util.List;

@Dao
public interface AppLogDAO {

    @Insert
    void insert(AppLog... appLogs);

    @Update
    void update(AppLog... appLogs);

    @Delete
    void delete(AppLog appLog);

    @Query(" SELECT * FROM " + AppDataBase.APPLOG_TABLE)
    List<AppLog> getAllItems();

    @Query(" SELECT * FROM " + AppDataBase.APPLOG_TABLE + " WHERE mItem = :itemname")
    List<AppLog> getItemsByItemname(String itemname);

    @Query(" SELECT * FROM " + AppDataBase.APPLOG_TABLE + " WHERE mUserSelling = :buyername")
    AppLog getItemByBuyerName(String buyername);

    @Query(" SELECT * FROM " + AppDataBase.APPLOG_TABLE + " WHERE mLogId = :IDNum")
    AppLog getListingByIDNumber(String IDNum);

    @Query(" SELECT * FROM " + AppDataBase.APPLOG_TABLE + " WHERE mUserId = :userId")
    List<AppLog> getListingsByUserId(int userId);


    @Insert
    void insert(User...users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserName = :username  ")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserId = :userId  ")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserId = :userIdNum ")
    User getUserToDeleteUser(String userIdNum);


    @Insert
    void insert(ShoppingCart...shoppingCarts);

    @Update
    void update(ShoppingCart... shoppingCarts);

    @Delete
    void delete(ShoppingCart shoppingCart);

    @Query(" SELECT * FROM " + AppDataBase.CART_TABLE + " WHERE mUserId = :userId")
    List<ShoppingCart> getAllItemsInCart(int userId);

    @Query(" SELECT * FROM " + AppDataBase.CART_TABLE + " WHERE mcartId = :IdNumber")
    ShoppingCart getItemByIdNumber(String IdNumber);


    @Insert
    void insert(Orders...orders);

    @Update
    void update(Orders...Orders);

    @Delete
    void delete(Orders order);

    @Query(" SELECT * FROM " + AppDataBase.ORDERS_TABLE + " WHERE mUserId = :userId")
    List<Orders> getAllOrders(int userId);

    @Query(" SELECT * FROM " + AppDataBase.ORDERS_TABLE + " WHERE mOrderId = :IdNumber")
    Orders getOrderByIdNumber(String IdNumber);
}
