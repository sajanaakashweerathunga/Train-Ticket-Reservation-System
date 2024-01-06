package com.example.ead_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UpdateReservation extends AppCompatActivity {

    EditText nic_input2, train_input2, start_input2, end_input2, date_input2, time_input2, seats_input2;
    Button update_button, delete_button, dateButton;

    String id, nic, train, start, end, date, time, seats;

    LocalDate currentDate = LocalDate.now();

    ImageView date_button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        nic_input2 = findViewById(R.id.nic_input2);
        train_input2 = findViewById(R.id.train_input2);
        start_input2 = findViewById(R.id.start_input2);
        end_input2 = findViewById(R.id.end_input2);
        date_input2 = findViewById(R.id.date_input2);
        time_input2 = findViewById(R.id.time_input2);
        seats_input2 = findViewById(R.id.seats_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        date_button2 = findViewById(R.id.date_button2);

        getAndSetIntentData();
        //set action bar title
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(train);
        }

        //update reservation method call
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateReservation.this);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

                String dateString1 = date;//get the date of reservation
                String dateString2 = currentDate.toString();// getting current date

                LocalDate date1 = LocalDate.parse(dateString1, formatter);
                LocalDate date2 = LocalDate.parse(dateString2, formatter);

                long daysBetween = Math.abs(date1.until(date2).getDays());
                
                if(daysBetween >= 5){
                    myDB.updateData(id, nic_input2.getText().toString(), train_input2.getText().toString(), start_input2.getText().toString(),
                            end_input2.getText().toString(), date_input2.getText().toString(), time_input2.getText().toString(), seats_input2.getText().toString());
//                    Toast.makeText(UpdateReservation.this, "days between = "+daysBetween, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(UpdateReservation.this, MainActivity.class);
//                    startActivity(intent);
                }else{
                    Toast.makeText(UpdateReservation.this, "Can't update; minimum 5 days require!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
                String dateString1 = date;//get the date of reservation
                String dateString2 = currentDate.toString();// getting current date

                LocalDate date1 = LocalDate.parse(dateString1, formatter);
                LocalDate date2 = LocalDate.parse(dateString2, formatter);

                long daysBetween = Math.abs(date1.until(date2).getDays());

                if(daysBetween >= 5){
                    confirmDialog();
//                    Toast.makeText(UpdateReservation.this, "days between = "+daysBetween, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateReservation.this, "Can't update; minimum 5 days require!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        date_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nic") && getIntent().hasExtra("train") && getIntent().hasExtra("start") &&
        getIntent().hasExtra("end") && getIntent().hasExtra("date") && getIntent().hasExtra("time") && getIntent().hasExtra("seats")){
            // getting data from intent
            id = getIntent().getStringExtra("id");
            nic = getIntent().getStringExtra("nic");
            train = getIntent().getStringExtra("train");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            seats = getIntent().getStringExtra("seats");

            //set data
            nic_input2.setText(nic);
            train_input2.setText(train);
            start_input2.setText(start);
            end_input2.setText(end);
            date_input2.setText(date);
            time_input2.setText(time);
            seats_input2.setText(seats);

        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ train + " Reservation on " + date + "?");
        builder.setMessage("Are you sure? You want to delete reservation?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateReservation.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(UpdateReservation.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Update your EditText or another view with the selected date
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Adjust month (0-based) to your needs
                // For example, if you want to set it in an EditText:
                date_input2.setText(selectedDate);
            }
        }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

}