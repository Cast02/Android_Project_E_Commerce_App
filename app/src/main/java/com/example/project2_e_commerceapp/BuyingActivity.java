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
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

import java.util.List;

public class BuyingActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project2_e_commerceapp.userId";
    private static final String PREFENCES_KEY = "com.example.project2_e_commerceapp.PREFENCES_KEY";
    private int mUserId = -1;
    private SharedPreferences mPreference = null;

    private Button mSearch;
    private Button mBuyItem;
    private EditText mNameOfSearchedItemField;
    private EditText mItemIDField;
    private TextView mItemInfo;

    private String mNameOfSearchedItem;
    private String mIDNumber;

    private List<AppLog> mItem;
    private AppLog mPurchase;
    private AppLogDAO mAppLogDAO;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);

        getDataBase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);
        wireUpDisplay();
        mItemInfo.setMovementMethod(new ScrollingMovementMethod());
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

    private void wireUpDisplay() {
        mSearch = findViewById(R.id.buttonToSearchForItem);
        mBuyItem = findViewById(R.id.ButtonToBuyItem);
        mItemIDField = findViewById(R.id.editTextNameOfBuyer);
        mNameOfSearchedItemField = findViewById(R.id.editTextNameOfItem);
        mItemInfo = findViewById(R.id.textViewSearchedItemInfo);
//        mSecondImage = findViewById(R.id.secondEgg);

        mBuyItem.setVisibility(View.INVISIBLE);
        mItemIDField.setVisibility(View.INVISIBLE);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if (checkToSeeIfItemExists()) {
                    refreshDisplay();
                    mBuyItem.setVisibility(View.VISIBLE);
                    mItemIDField.setVisibility(View.VISIBLE);
                    mItemIDField.setText("");
                    mItemIDField.setHint("ID Number");
                } else {
                    Toast.makeText(BuyingActivity.this, mNameOfSearchedItem + " was not found", Toast.LENGTH_SHORT).show();
                    mItemInfo.setText("");
                    mBuyItem.setVisibility(View.INVISIBLE);
                    mItemIDField.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBuyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDNumber = mItemIDField.getText().toString();
                mPurchase = mAppLogDAO.getListingByIDNumber(mIDNumber);
                if (mPurchase == null) {
                    Toast.makeText(BuyingActivity.this, "ID is not listed", Toast.LENGTH_SHORT).show();
                    mItemIDField.setText("");
                    mItemIDField.setHint("ID Number");

                } else {
                    ShoppingCart itemBeingAdded = new ShoppingCart(mPurchase.getItem(), mPurchase.getCost(), mPurchase.getGenre(), mPurchase.getCondition(), mPurchase.getUserSelling(), mUser.getUserId(), mPurchase.getUserId());
                    mAppLogDAO.insert(itemBeingAdded);
                    mItemInfo.setText("");
                    mBuyItem.setVisibility(View.INVISIBLE);
                    mItemIDField.setVisibility(View.INVISIBLE);
                    Toast.makeText(BuyingActivity.this, "Item was added to shopping cart", Toast.LENGTH_SHORT).show();
                    mNameOfSearchedItemField.setText("");
                    mNameOfSearchedItemField.setHint("Name of Funko Pop");
                    mAppLogDAO.delete(mPurchase);
                }
            }
        });
    }

    private boolean checkToSeeIfItemExists(){
        mItem = mAppLogDAO.getItemsByItemname(mNameOfSearchedItem);
        if(mItem.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    private void getValuesFromDisplay(){
        mNameOfSearchedItem = mNameOfSearchedItemField.getText().toString();
    }


    private void refreshDisplay() {
        mItem = mAppLogDAO.getItemsByItemname(mNameOfSearchedItem);

        if(!mItem.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(AppLog log: mItem){
                sb.append(log.toString()).append("\n").append("\n");
            }
            mItemInfo.setText(sb.toString());
        }else{
            mItemInfo.setText("");
        }
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, BuyingActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}