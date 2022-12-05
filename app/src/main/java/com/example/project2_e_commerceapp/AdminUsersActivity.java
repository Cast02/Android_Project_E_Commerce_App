package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
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

public class AdminUsersActivity extends AppCompatActivity {

    private AppLogDAO mAppLogDAO;

    private TextView mAllUsersDisplay;
    private EditText mIDNumberField;
    private Button mDeleteUser;
    private List<User> mAllUsers;

    private User mUserToDelete;
    private String mIDNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        getDataBase();
        wireUpDisplay();
        mAllUsersDisplay.setMovementMethod(new ScrollingMovementMethod());
        displayAllUsers();
    }

    private void getDataBase(){
        mAppLogDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AppLogDAO();
    }

    private void wireUpDisplay(){
        mAllUsersDisplay = findViewById(R.id.textViewTOViewAllUsers);
        mIDNumberField = findViewById(R.id.editTextToSelectUserToDelete);
        mDeleteUser = findViewById(R.id.buttonToDeleteUser);

        mDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDNumber = mIDNumberField.getText().toString();
                mUserToDelete = mAppLogDAO.getUserToDeleteUser(mIDNumber);
                if(mUserToDelete == null){
                    Toast.makeText(AdminUsersActivity.this, "This ID does not exist", Toast.LENGTH_SHORT).show();
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("Enter user ID Number");
                }else {
                    mAppLogDAO.delete(mUserToDelete);
                    mIDNumberField.setText("");
                    mIDNumberField.setHint("Enter user ID Number");
                    displayAllUsers();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayAllUsers(){
        mAllUsers = mAppLogDAO.getAllUsers();
        if(!mAllUsers.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(User log: mAllUsers){
                sb.append(log.toString()).append("\n").append("\n");
            }
            mAllUsersDisplay.setText(sb.toString());
        }else{
            mAllUsersDisplay.setText("There are no users at this time");
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminUsersActivity.class);
        return intent;
    }
}