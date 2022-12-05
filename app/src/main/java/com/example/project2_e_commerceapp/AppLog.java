package com.example.project2_e_commerceapp;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_e_commerceapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.APPLOG_TABLE)
public class AppLog {

    @PrimaryKey(autoGenerate = true)
    private int mLogId;

    private String mItem;
    private String mCost;
    private String mGenre;
    private String mCondition;
    private String mUserSelling;
    private int mUserId;

    public AppLog(String item, String cost, String genre, String condition, String userSelling, int userId) {
        mItem = item;
        mCost = cost;
        mGenre = genre;
        mCondition = condition;
        mUserSelling = userSelling;
        mUserId = userId;
    }

    @Override
    public String toString() {
        return
                "Name: " + mItem + '\n' +
                "Price: $" + mCost + '\n' +
                "Genre: " + mGenre + '\n' +
                        "Condition: " + mCondition +'\n' +
                        "Sold by: " + mUserSelling + '\n' +
                        "ID: " + mLogId
                ;
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

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        mCondition = condition;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int logId) {
        mLogId = logId;
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
}
