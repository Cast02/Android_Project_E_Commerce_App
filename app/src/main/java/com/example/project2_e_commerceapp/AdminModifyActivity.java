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

public class AdminModifyActivity extends AppCompatActivity {

    private TextView mAllItemsDisplay;
    private EditText mIDNumberField;
    private Button mModifyItem;

    private String mIDNumber;
    private AppLogDAO mAppLogDAO;
    private List<AppLog> mAllListings;
    private AppLog mItemToModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify);

        getDataBase();
        wireUpDisplay();
        mAllItemsDisplay.setMovementMethod(new ScrollingMovementMethod());
        displayAllListings();


    }
    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void wireUpDisplay(){
        mAllItemsDisplay = findViewById(R.id.textViewToViewAllListingsToModify);
        mIDNumberField = findViewById(R.id.editTextToSelectItemFromListingsToModify);
        mModifyItem = findViewById(R.id.buttonToModifyItemFromAllListings);

        mModifyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDNumber = mIDNumberField.getText().toString();
                mItemToModify = mAppLogDAO.getListingByIDNumber(mIDNumber);
                if(mItemToModify == null){
                    Toast.makeText(AdminModifyActivity.this, "This ID does not exist", Toast.LENGTH_SHORT).show();
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("ID Number");
                }else {
                    Intent intent = AdminModifyDataBaseActivity.intentFactory(getApplicationContext(), mIDNumber);
                    startActivity(intent);
                }
            }
        });
    }

    private void displayAllListings(){
        mAllListings = mAppLogDAO.getAllItems();
        if(!mAllListings.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(AppLog log: mAllListings){
                sb.append(log.toString()).append("\n").append("\n");
            }
            mAllItemsDisplay.setText(sb.toString());
        }else{
            mAllItemsDisplay.setText("There are no items listed at this time");
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminModifyActivity.class);
        return intent;
    }
}