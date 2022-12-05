package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

public class AdminModifyDataBaseActivity extends AppCompatActivity {

    private static String mIDNumber;
    private AppLogDAO mAppLogDAO;
    private AppLog mItemToModify;

    private EditText mNewNameField;
    private EditText mNewPriceField;
    private EditText mNewGenreField;
    private EditText mNewConditionField;
    private Button mApplyChanges;

    private String mNewName;
    private String mNewPrice;
    private String mNewGenre;
    private String mNewCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_data_base);

        getDataBase();
        wireUpDisplay();
    }

    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void wireUpDisplay(){
        mNewNameField = findViewById(R.id.editTextNameModification);
        mNewPriceField = findViewById(R.id.editTextPriceModification);
        mNewGenreField = findViewById(R.id.editTextGenreModification);
        mNewConditionField = findViewById(R.id.editTextConditionModification);
        mApplyChanges = findViewById(R.id.buttonToApplyModifications);

        mItemToModify = mAppLogDAO.getListingByIDNumber(mIDNumber);
        setHintValues(mItemToModify);

        mApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                AppLog mModifiedItem = new AppLog(mNewName, mNewPrice, mNewGenre, mNewCondition, mItemToModify.getUserSelling(), mItemToModify.getUserId());
                mAppLogDAO.insert(mModifiedItem);
                mAppLogDAO.delete(mItemToModify);
                Intent intent = AdminModifyActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void getValuesFromDisplay(){
        mNewName = mNewNameField.getText().toString();
        mNewPrice = mNewPriceField.getText().toString();
        mNewGenre = mNewGenreField.getText().toString();
        mNewCondition = mNewConditionField.getText().toString();
    }

    private void setHintValues(AppLog item){
        mNewNameField.setText(item.getItem());
        mNewPriceField.setText(item.getCost());
        mNewGenreField.setText(item.getGenre());
        mNewConditionField.setText(item.getCondition());
    }

    public static Intent intentFactory(Context context, String idNumber){
        Intent intent = new Intent(context, AdminModifyDataBaseActivity.class);
        mIDNumber = idNumber;
        return intent;
    }
}