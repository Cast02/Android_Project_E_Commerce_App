package com.example.project2_e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2_e_commerceapp.DB.AppDataBase;
import com.example.project2_e_commerceapp.DB.AppLogDAO;

public class CreatingNewAccount extends AppCompatActivity {

    private AppLogDAO mAppLogDAO;
    private User mUser;

    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mUserNameField;
    private EditText mPasswordField;

    private String mFirstName;
    private String mLastName;
    private String mUserName;
    private String mPassword;

    private Button mCreateNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_new_account);

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
        mFirstNameField = findViewById(R.id.editTextUserFirstName);
        mLastNameField = findViewById(R.id.editTextUserLastName);
        mUserNameField = findViewById(R.id.editTextNewUserUserName);
        mPasswordField = findViewById(R.id.editTextNewUserPassword);
        mCreateNewUser = findViewById(R.id.buttonCreateNewUser);

        mCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    User newUser = new User(mFirstName,mLastName,mUserName, mPassword, false);
                    mAppLogDAO.insert(newUser);
                    Intent intent = MainActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                }

            }
        });

    }

    private void getValuesFromDisplay() {
        mFirstName = mFirstNameField.getText().toString();
        mLastName = mLastNameField.getText().toString();
        mUserName = mUserNameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser = mAppLogDAO.getUserByUsername(mUserName);
        if(mUser != null){
            Toast.makeText(this, mUserName + " is already taken.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            Toast.makeText(this, " Account has been created", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, CreatingNewAccount.class);
        return intent;
    }
}