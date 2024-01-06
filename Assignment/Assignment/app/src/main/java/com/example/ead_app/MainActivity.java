package com.example.ead_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> res_id,res_nic, res_train, res_start, res_end, res_date, res_time, res_seats;
    CustomAdapter customAdapter;

    String nic, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the values of 'nic' and 'mail' from the intent extras
         nic = getIntent().getStringExtra("nic");
         mail = getIntent().getStringExtra("mail");

        recyclerview = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateReservation.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        res_id = new ArrayList<>();
        res_nic = new ArrayList<>();
        res_train = new ArrayList<>();
        res_start = new ArrayList<>();
        res_end = new ArrayList<>();
        res_date = new ArrayList<>();
        res_time = new ArrayList<>();
        res_seats = new ArrayList<>();


        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this, MainActivity.this, res_id, res_nic, res_train, res_start, res_end, res_date, res_time, res_seats);
        recyclerview.setAdapter(customAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readDataByNIC(nic);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Reservations data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                res_id.add(cursor.getString(0));
                res_nic.add(cursor.getString(1));
                res_train.add(cursor.getString(2));
                res_start.add(cursor.getString(3));
                res_end.add(cursor.getString(4));
                res_date.add(cursor.getString(5));
                res_time.add(cursor.getString(6));
                res_seats.add(cursor.getString(7));
            }
        }
    }


}