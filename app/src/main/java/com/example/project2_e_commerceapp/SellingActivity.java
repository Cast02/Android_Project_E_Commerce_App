package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

public class SellingActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project2_e_commerceapp.userId";
    private static final String PREFENCES_KEY = "com.example.project2_e_commerceapp.PREFENCES_KEY";
    private SharedPreferences mPreference = null;

    private EditText mItemNameField;
    private EditText mItemPriceField;
    private EditText mItemGenereField;
    private EditText mItemConditionField;
    private Button mSubmitNewItem;


    private String mItemName;
    private String mItemPrice;
    private String mItemGenere;
    private String mItemCondition;

    private AppLogDAO mAppLogDAO;
    private User mUser;

    private int mUserId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);

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
        mItemNameField = findViewById(R.id.editTextNameModification);
        mItemPriceField = findViewById(R.id.editTextPriceModification);
        mItemGenereField = findViewById(R.id.editTextGenreModification);
        mItemConditionField = findViewById(R.id.editTextConditionModification);

        mSubmitNewItem = findViewById(R.id.buttonToApplyModifications);


        mSubmitNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                AppLog newItem = new AppLog(mItemName, mItemPrice, mItemGenere, mItemCondition, mUser.getUserName(), mUserId);
                mAppLogDAO.insert(newItem);
                Toast.makeText(SellingActivity.this, "Item was added", Toast.LENGTH_SHORT).show();
                Intent intent = LandingPage.intentFactory(getApplicationContext(), mUser.getUserId());
                startActivity(intent);
            }
        });

    }

    private void getValuesFromDisplay(){
        mItemName = mItemNameField.getText().toString();
        mItemPrice = mItemPriceField.getText().toString();
        mItemGenere = mItemGenereField.getText().toString();
        mItemCondition = mItemConditionField.getText().toString();
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, SellingActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}