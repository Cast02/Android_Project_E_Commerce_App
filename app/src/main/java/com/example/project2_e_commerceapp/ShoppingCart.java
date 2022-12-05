package com.example.project2_e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_e_commerceapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.CART_TABLE)
public class ShoppingCart {
    @PrimaryKey(autoGenerate = true)
    private int mcartId;

    private String mItem;
    private String mCost;
    private String mGenre;
    private String mCondition;
    private String mUserSelling;
    private int mUserId;
    private int mBuyerId;

    public ShoppingCart(String item, String cost, String genre, String condition, String userSelling, int userId, int buyerId) {
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
                "ID Number: " + mcartId;
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

    public int getMcartId() {
        return mcartId;
    }

    public void setMcartId(int mcartId) {
        this.mcartId = mcartId;
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
}
