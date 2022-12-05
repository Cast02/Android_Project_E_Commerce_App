package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mCreateAccount;
    private AppLogDAO mAppLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataBase();
        checkForUser();
        wireUpDisplay();
    }

    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void checkForUser(){
        List<User> users = mAppLogDAO.getAllUsers();
        List<AppLog> items = mAppLogDAO.getAllItems();
        if(users.size() <= 0){
            User adminUser = new User("Admin","User","admin2", "admin2", true);
            User testUser = new User("Test", "User", "testuser1", "testuser1", false);
            mAppLogDAO.insert(adminUser, testUser);
        }

        if(items.size() <= 0){
            AppLog Item1 = new AppLog("Beast Titan", "140.00", "Anime", "Slight Damage", "admin2", 1);
            AppLog Item2 = new AppLog("Levi", "20.00", "Anime", "Minor Damage", "admin2", 1);
            AppLog Item3 = new AppLog("Alexander Hamilton", "15.00", "Musical", "Mint", "admin2", 1);
            AppLog Item4 = new AppLog("Mickey Mouse", "20.00", "Disney", "Damaged", "admin2", 1);
            AppLog Item5 = new AppLog("Beast Titan", "200.00", "Anime", "Mint", "testuser1", 2);
            AppLog Item6 = new AppLog("Levi", "10.00", "Anime", "Damaged", "testuser1", 2);
            AppLog Item7 = new AppLog("Monkey Bomb", "50.00", "Video Games", "Mint", "testuser1", 2);
            AppLog Item8 = new AppLog("Colossal Titan", "180.00", "Anime", "Mint", "testuser1", 2);


            mAppLogDAO.insert(Item1, Item2, Item3, Item4, Item5, Item6, Item7, Item8);
        }
    }

    private void wireUpDisplay() {
        mLogin = findViewById(R.id.LoginButton);
        mCreateAccount = findViewById(R.id.CreateUserButton);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreatingNewAccount.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }


}