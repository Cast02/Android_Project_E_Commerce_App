package com.example.project2_e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_e_commerceapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.ORDERS_TABLE)
public class Orders {

    @PrimaryKey(autoGenerate = true)
    private int mOrderId;

    private String mItem;
    private String mCost;
    private String mGenre;
    private String mCondition;
    private String mUserSelling;
    private int mUserId;
    private int mBuyerId;

    public Orders(String item, String cost, String genre ,String condition, String userSelling, int userId, int buyerId) {
        mItem = item;
        mCost = cost;
        mGenre = genre;
        mCondition = condition;
        mUserSelling = userSelling;
        mUserId = userId;
        mBuyerId = buyerId;
    }

    @Override
    public String toString() {
        return "Name: " + mItem + '\n' +
                "Price: $" + mCost + '\n' +
                "Genre: " + mGenre + '\n' +
                "Condition: " + mCondition +'\n' +
                "Sold by: " + mUserSelling + '\n' +
                "ID Number: " + mOrderId;
    }

    public int getBuyerId() {
        return mBuyerId;
    }

    public void setBuyerId(int buyerId) {
        mBuyerId = buyerId;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public int getOrderId() {
        return mOrderId;
    }

    public void setOrderId(int orderId) {
        mOrderId = orderId;
    }

    public String getItem() {
        return mItem;
    }

    public void setItem(String item) {
        mItem = item;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        mCondition = condition;
    }

    public String getUserSelling() {
        return mUserSelling;
    }

    public void setUserSelling(String userSelling) {
        mUserSelling = userSelling;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }
}
