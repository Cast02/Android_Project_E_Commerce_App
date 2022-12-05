package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

public class AdminActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project2_e_commerceapp.userId";
    private static final String PREFENCES_KEY = "com.example.project2_e_commerceapp.PREFENCES_KEY";
    private int mUserId = -1;
    private SharedPreferences mPreference = null;

    private AppLogDAO mAppLogDAO;

    private User mUser;

    private Button mViewAllListings;
    private Button mViewAllUsers;
    private Button mModifyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getDataBase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);
        wireUpDisplay();
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
        mViewAllListings = findViewById(R.id.buttonToViewAllListings);
        mViewAllUsers = findViewById(R.id.buttonToViewAllUsers);
        mModifyItem = findViewById(R.id.buttonToModifyAnItem);

        mViewAllListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminListingsActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mViewAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminUsersActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mModifyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminModifyActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }


    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}