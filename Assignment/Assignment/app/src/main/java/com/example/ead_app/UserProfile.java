package com.example.ead_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {

    TextView  textNic;
    String username,nic,mail,mobile,password;

    EditText editTextTextPersonName, editTextEmail, editTextMobile, editTextPassword;

    Button updateButton, deActivateButton, viewTicketsButton, createTicketButton;

    private MyDatabaseHelper dbHelper;

    ImageView imageUpdate;
    
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        dbHelper = new MyDatabaseHelper(this);

        // Retrieve the userInfor object from Intent extras
        User userInfor = getIntent().getParcelableExtra("userInfor");

        username = userInfor.getUserName();
        nic = userInfor.getNIC();
        mail = userInfor.getEmail();
        mobile = userInfor.getMobile();
        password = userInfor.getPassword();

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        textNic = findViewById(R.id.textNIC);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);

        editTextTextPersonName.setText(username);
        textNic.setText(nic);
        editTextEmail.setText(mail);
        editTextMobile.setText(mobile);
        editTextPassword.setText(password);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        View dialogView2 = getLayoutInflater().inflate(R.layout.deactivated_error_dialog, null);
        builder.setView(dialogView2);
        AlertDialog dialog2 = builder.create();


        imageUpdate = findViewById(R.id.imageUpdate);
        imageUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                    if(user.isActive() == true){
                        String new_nic = nic;
                        String new_name = editTextTextPersonName.getText().toString();
                        String new_mail = editTextEmail.getText().toString();
                        String new_mobile = editTextMobile.getText().toString();
                        String new_password = editTextPassword.getText().toString();

                        User new_user = new User();
                        new_user.setUserName(new_name);
                        new_user.setNIC(new_nic);
                        new_user.setEmail(new_mail);
                        new_user.setMobile(new_mobile);
                        new_user.setPassword(new_password);

                        dbHelper.updateUserByNIC(nic, new_user);
                        dialog.show();
                    }else {
                        Toast.makeText(UserProfile.this, "User not in active state", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        deActivateButton = findViewById(R.id.deActivateButton);
        deActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteUserByNIC(nic);
                confirmDialog();
            }
        });

        viewTicketsButton =findViewById(R.id.viewTicketsButton);
        viewTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.isActive() == true){
                    Intent intent = new Intent(UserProfile.this, MainActivity.class);
                    intent.putExtra("nic", nic);
                    startActivity(intent);
                }else {
                    dialog2.show();
                }
            }
        });

        createTicketButton = findViewById(R.id.createTicketButton);
        createTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.isActive() == true){
                    Intent intent = new Intent(UserProfile.this, CreateReservation.class);
                    intent.putExtra("nic", nic);
                    startActivity(intent);
                }else {
                    dialog2.show();
                }
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // When the password field gains focus (is clicked), show the plain text
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // When the password field loses focus (user clicks outside), set it back to password input type
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });

    }

    private void confirmDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle(" Are you sure?");
        builder.setMessage("Do you want to deactivate your account?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user.setActive(false);
                Toast.makeText(UserProfile.this, "Account Deactivated!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserProfile.this, SplashActivity.class);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}