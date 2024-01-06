package com.example.ead_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class SplashActivity extends AppCompatActivity {

    Button loginButton2, signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        signUpButton = findViewById(R.id.signUpButton);

        loginButton2 = findViewById(R.id.loginButton2);
        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the Sign Up page
                Intent intent = new Intent(SplashActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}