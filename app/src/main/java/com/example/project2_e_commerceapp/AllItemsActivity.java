package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

import java.util.List;

public class AllItemsActivity extends AppCompatActivity {

    private TextView mListingsDisplay;
    private List<AppLog> mAllListingsForAllUsers;
    private AppLogDAO mAppLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        getDataBase();
        wireUpDisplay();
        mListingsDisplay.setMovementMethod(new ScrollingMovementMethod());
        displayAllListings();
    }



    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void wireUpDisplay(){
        mListingsDisplay = findViewById(R.id.textViewToViewAllListingsForAllUsers);
    }

    private void displayAllListings(){
       mAllListingsForAllUsers = mAppLogDAO.getAllItems();
        if(!mAllListingsForAllUsers.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(AppLog log: mAllListingsForAllUsers){
                sb.append(log.toString()).append("\n").append("\n");
            }
            mListingsDisplay.setText(sb.toString());
        }else{
            mListingsDisplay.setText("There are no items listed at this time");
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AllItemsActivity.class);
        return intent;
    }
}