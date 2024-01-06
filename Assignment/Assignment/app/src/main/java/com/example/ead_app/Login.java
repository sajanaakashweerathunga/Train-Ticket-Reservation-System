package com.example.ead_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button signInButton;

    TextView txtRegister;
    private MyDatabaseHelper dbHelper;

    String name,nic,mail,mobile,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        dbHelper = new MyDatabaseHelper(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        signInButton = findViewById(R.id.btnsignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<User> userDetails = dbHelper.LoginUser(edtEmail.getText().toString(), edtPassword.getText().toString());
                User userInfor = dbHelper.findUserByNIC(edtEmail.getText().toString());
                name = userInfor.getUserName();
                nic = userInfor.getNIC();
                mail = userInfor.getEmail();
                mobile = userInfor.getMobile();
                pw = userInfor.getPassword();


                if(userDetails.size() != 0){
                    User user = userDetails.get(0);
                    Toast.makeText(Login.this, "Valid Login", Toast.LENGTH_SHORT).show();

                    // Create a Gson instance
                    Gson gson = new Gson();
                    // Serialize User to JSON
                    String userJson = gson.toJson(user);

                    Intent intent = new Intent(Login.this, UserProfile.class);
                    intent.putExtra("userInfor", userInfor);
                    startActivity(intent);

                }else {
                    Toast.makeText(Login.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // When the password field gains focus (is clicked), show the plain text
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // When the password field loses focus (user clicks outside), set it back to password input type
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });

    }

//    private boolean CheckAllFields() {
//        if (edtEmail.length() == 3) {
//            edtEmail.setError("This field is required");
//            return false;
//        }
//
//        else if (edtPassword.length() == 3) {
//            edtPassword.setError("This field is required");
//            return false;
//        }
//
//        // after all validation return true.
//        return true;
//    }
}