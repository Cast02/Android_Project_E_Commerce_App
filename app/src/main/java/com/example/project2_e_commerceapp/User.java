package com.example.project2_e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_e_commerceapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mFirstName;
    private String mLastName;
    private String mUserName;
    private String mPassword;
    private boolean mIsAdmin;

    public User(String firstName, String lastName, String userName, String password, boolean isAdmin) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserName = userName;
        mPassword = password;
        mIsAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return
                "First Name: " + mFirstName + '\n' +
                "LastName: " + mLastName + '\n' +
                "UserName: " + mUserName + '\n' +
                "UserID: " + mUserId + '\n'
                 ;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
