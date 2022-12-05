package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

import java.util.List;

public class AdminListingsActivity extends AppCompatActivity {

    private TextView mAllListingsDisplay;
    private EditText mIDNumberField;
    private Button mDeleteItem;

    private String mIDNumber;
    private AppLogDAO mAppLogDAO;
    private List<AppLog> mAllListings;
    private AppLog mItemToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listings);

        getDataBase();
        wireUpDisplay();
        mAllListingsDisplay.setMovementMethod(new ScrollingMovementMethod());
        displayAllListings();
    }

    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void wireUpDisplay(){
        mAllListingsDisplay = findViewById(R.id.textViewToViewAllListingsToModify);
        mIDNumberField = findViewById(R.id.editTextToSelectItemFromListingsToModify);
        mDeleteItem = findViewById(R.id.buttonToModifyItemFromAllListings);

        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDNumber = mIDNumberField.getText().toString();
                mItemToDelete = mAppLogDAO.getListingByIDNumber(mIDNumber);
                if(mItemToDelete == null){
                    Toast.makeText(AdminListingsActivity.this, "This ID does not exist", Toast.LENGTH_SHORT).show();
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("ID Number");
                }else {
                    mAppLogDAO.delete(mItemToDelete);
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("ID Number");
                    displayAllListings();
                }
            }
        });

    }

    private void displayAllListings() {
        mAllListings = mAppLogDAO.getAllItems();
        if (!mAllListings.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (AppLog log : mAllListings) {
                sb.append(log.toString()).append("\n").append("\n");
            }
            mAllListingsDisplay.setText(sb.toString());
        } else {
            mAllListingsDisplay.setText("There are no items listed at this time");
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminListingsActivity.class);
        return intent;
    }
}