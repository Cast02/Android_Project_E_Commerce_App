package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project2_e_commerceapp.userId";
    private static final String PREFENCES_KEY = "com.example.project2_e_commerceapp.PREFENCES_KEY";
    private int mUserId = -1;
    private SharedPreferences mPreference = null;

    private AppLogDAO mAppLogDAO;
    private User mUser;
    private List<AppLog> mListings;

    private TextView mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getDataBase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);
        wireUpDisplay();
        mUserInfo.setMovementMethod(new ScrollingMovementMethod());
        displayUserInfo();

    }

    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }

        if(mPreference == null){
            getPrefs();
        }

        mUserId = mPreference.getInt(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }
    }

    private void getPrefs() {
        mPreference = this.getSharedPreferences(PREFENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {
        if(mPreference == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putInt(USER_ID_KEY, userId);
    }

    private void loginUser(int userId) {
        mUser = mAppLogDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    private void wireUpDisplay(){
        mUserInfo = findViewById(R.id.textViewToDisplayProfile);
    }

    private void displayUserInfo(){
        mUser = mAppLogDAO.getUserByUserId(mUserId);
        mListings = mAppLogDAO.getListingsByUserId(mUserId);

        StringBuilder sb = new StringBuilder();
        sb.append("First Name: ").append(mUser.getFirstName()).append("\n");
        sb.append("Last Name: ").append(mUser.getLastName()).append("\n");
        sb.append("UserName: ").append(mUser.getUserName()).append("\n").append("\n");

        if(!mListings.isEmpty()) {
            sb.append("Listings: ").append("\n").append("\n");
            for (AppLog log : mListings) {
                sb.append(log.toString()).append("\n").append("\n");
            }
        } else{
            sb.append("Go to the 'Sell' section to put any Funko Pops you have for sell!");
        }
        mUserInfo.setText(sb.toString());
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}