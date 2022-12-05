package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
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

public class OrdersActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project2_e_commerceapp.userId";
    private static final String PREFENCES_KEY = "com.example.project2_e_commerceapp.PREFENCES_KEY";
    private int mUserId = -1;
    private SharedPreferences mPreference = null;

    private AppLogDAO mAppLogDAO;
    private User mUser;
    private Orders mOrderToDelete;
    private AppLog mItemToBePutBack;
    private List<Orders> mOrderList;

    private TextView mOrderListDisplay;
    private TextView mInformativeText;
    private EditText mIDNumberField;
    private Button mCancelOrder;

    private String mIDNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getDataBase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);
        wireUpDisplay();
        mOrderListDisplay.setMovementMethod(new ScrollingMovementMethod());
        displayOrderList();

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
        mOrderListDisplay = findViewById(R.id.textViewOrdersList);
        mInformativeText = findViewById(R.id.textViewOrders);
        mIDNumberField = findViewById(R.id.editTextIdNumberToCancelOrder);
        mCancelOrder = findViewById(R.id.buttonToCancelOrder);

        mCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDNumber = mIDNumberField.getText().toString();
                mOrderToDelete = mAppLogDAO.getOrderByIdNumber(mIDNumber);
                if(mOrderToDelete == null){
                    Toast.makeText(OrdersActivity.this, "ID is not listed", Toast.LENGTH_SHORT).show();
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("ID Number");
                }else {
                    mItemToBePutBack = new AppLog(mOrderToDelete.getItem(), mOrderToDelete.getCost(),
                            mOrderToDelete.getGenre(), mOrderToDelete.getCondition(),
                            mOrderToDelete.getUserSelling(), mOrderToDelete.getBuyerId());
                    mAppLogDAO.insert(mItemToBePutBack);
                    mAppLogDAO.delete(mOrderToDelete);
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("ID Number");
                    displayOrderList();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayOrderList(){
        mOrderList = mAppLogDAO.getAllOrders(mUserId);
        if(!mOrderList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(Orders log: mOrderList){
                sb.append(log.toString()).append("\n").append("\n");
            }
            mOrderListDisplay.setText(sb.toString());
            mIDNumberField.setVisibility(View.VISIBLE);
            mCancelOrder.setVisibility(View.VISIBLE);
            mInformativeText.setVisibility(View.VISIBLE);
        }else{
            mOrderListDisplay.setText("Make sure to checkout your items from your shopping cart!");
            mIDNumberField.setVisibility(View.INVISIBLE);
            mCancelOrder.setVisibility(View.INVISIBLE);
            mInformativeText.setVisibility(View.INVISIBLE);
        }
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, OrdersActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}