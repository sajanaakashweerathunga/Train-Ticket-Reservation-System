package com.example.ead_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Register extends AppCompatActivity {

    TextView txtsignIn;
    EditText edtUserName, edtNIC, edtPhoneNo, edtEmail, edtPassword, edtPassword2;

    Button btnRegister;

    private MyDatabaseHelper dbHelper;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtsignIn = findViewById(R.id.txtsignIn);
        txtsignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        dbHelper = new MyDatabaseHelper(this);

        edtUserName = findViewById(R.id.edtUserName);
        edtNIC = findViewById(R.id.edtNIC);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword2 = findViewById(R.id.edtPassword2);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if(! isAllFieldsChecked){
                    Toast.makeText(Register.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }else if (!edtPassword.getText().toString().trim().equals(edtPassword2.getText().toString().trim())){
                    Toast.makeText(Register.this, "Passwords Mismatching", Toast.LENGTH_SHORT).show();
                }else {
                    User user = new User(edtUserName.getText().toString().trim(),
                            edtNIC.getText().toString().trim(),edtEmail.getText().toString().trim(),
                            edtPhoneNo.getText().toString().trim(), edtPassword.getText().toString().trim());
                    if(dbHelper.RegisterUser(user)){
                        Toast.makeText(Register.this, "Registered Success!!!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Register.this, Login.class);
                        startActivity(intent2);
                    }

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

        edtPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // When the password field gains focus (is clicked), show the plain text
                    edtPassword2.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // When the password field loses focus (user clicks outside), set it back to password input type
                    edtPassword2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }

    private boolean CheckAllFields() {
        if (edtUserName.length() == 0) {
            edtUserName.setError("This field is required");
            return false;
        }

        if (edtNIC.length() == 0) {
            edtNIC.setError("This field is required");
            return false;
        }else if (edtNIC.length() < 10 || edtNIC.length() > 12){
            edtNIC.setError("Incorrect Format");
            return false;
        }

        if (edtPhoneNo.length() == 0) {
            edtPhoneNo.setError("This field is required");
            return false;
        }else if (edtPhoneNo.length() > 10 || edtPhoneNo.length() < 10){
            edtPhoneNo.setError("Incorrect Format");
            return false;
        }

        if (edtEmail.length() == 0) {
            edtEmail.setError("Email is required");
            return false;
        } else if (edtPassword.length() < 8) {
            edtPassword.setError("Password must be minimum 8 characters");
            return false;
        }

        if (edtPassword2.length() == 0) {
            edtPassword2.setError("Password is required");
            return false;
        } else if (edtPassword2.length() < 8) {
            edtPassword2.setError("Password must be minimum 8 characters");
            return false;
        }

        // after all validation return true.
        return true;
    }

//    @SuppressLint("LongLogTag")
//    protected void sendEmail() {
//        String emailsend = edtEmail.getText().toString();
//        String emailsubject = edtUserName.getText().toString();
//        String emailbody = edtPassword.getText().toString();
//
//        // define Intent object with action attribute as ACTION_SEND
//        Intent intent = new Intent(Intent.ACTION_SEND);
//
//        // add three fields to intent using putExtra function
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
//        intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
//        intent.putExtra(Intent.EXTRA_TEXT, emailbody);
//
//        // set type of intent
//        intent.setType("message/rfc822");
//
//        // startActivity with intent with chooser as Email client using createChooser function
//        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
//    }

}