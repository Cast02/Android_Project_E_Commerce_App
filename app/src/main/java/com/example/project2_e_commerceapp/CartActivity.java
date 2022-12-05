package com.example.project2_e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project2_e_commerceapp.userId";
    private static final String PREFENCES_KEY = "com.example.project2_e_commerceapp.PREFENCES_KEY";
    private int mUserId = -1;
    private SharedPreferences mPreference = null;



    private TextView mCartListDisplay;
    private TextView mCartInfo;
    private EditText mIdNumberField;
    private Button mDeleteItem;
    private Button mAddItemToOrders;

    private String mIdNumber;

    private AppLogDAO mAppLogDAO;
    private ShoppingCart mItemToDelete;
    private ShoppingCart mItemToAdd;
    private AppLog mItemToBePutBack;
    private List<ShoppingCart> mCartList;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getDataBase();

        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);

        wireUpDisplay();
        mCartListDisplay.setMovementMethod(new ScrollingMovementMethod());
        displayCartList();

    }

    private void getDataBase() {
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }

        if (mPreference == null) {
            getPrefs();
        }

        mUserId = mPreference.getInt(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }
    }

    private void getPrefs() {
        mPreference = this.getSharedPreferences(PREFENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {
        if (mPreference == null) {
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
        mCartListDisplay = findViewById(R.id.textViewCartList);
        mIdNumberField = findViewById(R.id.editTextIDNumberSelection);
        mDeleteItem = findViewById(R.id.buttonToDeleteItemFromCart);
        mAddItemToOrders = findViewById(R.id.buttonToAddToOrderList);
        mCartInfo = findViewById(R.id.textViewCartInformation);

        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIdNumber = mIdNumberField.getText().toString();
                mItemToDelete = mAppLogDAO.getItemByIdNumber(mIdNumber);
                if (mItemToDelete == null){
                    Toast.makeText(CartActivity.this, "ID is not listed", Toast.LENGTH_SHORT).show();
                    mIdNumberField.setText("");
                    mIdNumberField.setHint("ID Number");
                }else {
                    mItemToBePutBack = new AppLog(
                            mItemToDelete.getItem(), mItemToDelete.getCost(),
                            mItemToDelete.getGenre(), mItemToDelete.getCondition(),
                            mItemToDelete.getUserSelling(), mItemToDelete.getBuyerId());

                    mAppLogDAO.insert(mItemToBePutBack);
                    mAppLogDAO.delete(mItemToDelete);
                    mIdNumberField.setText("");
                    mIdNumberField.setHint("ID Number");

                    displayCartList();
                }
            }
        });

        mAddItemToOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIdNumber = mIdNumberField.getText().toString();
                mItemToAdd = mAppLogDAO.getItemByIdNumber(mIdNumber);
                if (mItemToAdd == null) {
                    Toast.makeText(CartActivity.this, "ID is not listed", Toast.LENGTH_SHORT).show();
                    mIdNumberField.setText("");
                    mIdNumberField.setHint("ID Number");
                }else {
                    Orders newOrderToAdd = new Orders(mItemToAdd.getItem(), mItemToAdd.getCost(), mItemToAdd.getGenre(), mItemToAdd.getCondition(), mItemToAdd.getUserSelling(), mUser.getUserId(), mItemToAdd.getBuyerId());
                    mAppLogDAO.insert(newOrderToAdd);
                    mAppLogDAO.delete(mItemToAdd);
                    mIdNumberField.setText("");
                    mIdNumberField.setHint("ID Number");
                    displayCartList();
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void displayCartList() {
        mCartList = mAppLogDAO.getAllItemsInCart(mUserId);
        if (!mCartList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ShoppingCart log : mCartList) {
                sb.append(log.toString()).append("\n").append("\n");
            }
            mCartListDisplay.setText(sb.toString());


            mIdNumberField.setVisibility(View.VISIBLE);
            mDeleteItem.setVisibility(View.VISIBLE);
            mAddItemToOrders.setVisibility(View.VISIBLE);
        } else {
            mCartListDisplay.setText("There is nothing here! Go check out what is for sale!");
            mIdNumberField.setVisibility(View.INVISIBLE);
            mDeleteItem.setVisibility(View.INVISIBLE);
            mAddItemToOrders.setVisibility(View.INVISIBLE);
            mCartInfo.setText("");
        }
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}

