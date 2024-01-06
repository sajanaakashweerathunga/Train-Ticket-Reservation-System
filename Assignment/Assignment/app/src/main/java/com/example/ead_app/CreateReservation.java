package com.example.ead_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class CreateReservation extends AppCompatActivity {

    EditText nic_input, train_input, start_input, end_input, date_input, time_input, seats_input;
    Button add_button;

    ImageView dateButton;

    RadioGroup classRadioGroup; // Add this variable
    String selectedClass; // To store the selected class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        // Retrieve the values of 'nic' and 'mail' from the intent extras
        String nic = getIntent().getStringExtra("nic");

        nic_input = findViewById(R.id.nic_input);
        train_input = findViewById(R.id.train_input);
        start_input = findViewById(R.id.start_input);
        end_input = findViewById(R.id.end_input);
        date_input = findViewById(R.id.date_input);
        // Initialize the RadioGroup and selectedClass
        classRadioGroup = findViewById(R.id.class_radio_group);
        selectedClass = "";
        seats_input = findViewById(R.id.seats_input);
        add_button = findViewById(R.id.add_button);
        dateButton = findViewById(R.id.date_button);

        nic_input.setText(nic);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.reservation_confirm, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Set a listener to capture the selected class
        classRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which RadioButton was selected
                if (checkedId == R.id.radio_button_1st_class) {
                    selectedClass = "1st Class";
                } else if (checkedId == R.id.radio_button_2nd_class) {
                    selectedClass = "2nd Class";
                } else if (checkedId == R.id.radio_button_3rd_class) {
                    selectedClass = "3rd Class";
                } else {
                    selectedClass = ""; // None selected
                }
            }
        });

        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(CreateReservation.this);
                myDB.addReservation(nic_input.getText().toString().trim(),
                        train_input.getText().toString().trim(),
                        start_input.getText().toString().trim(),
                        end_input.getText().toString().trim(),
                        date_input.getText().toString().trim(),
                        selectedClass,
                        Integer.valueOf(seats_input.getText().toString().trim()));
                dialog.show();
                Intent intent = new Intent(CreateReservation.this, UserProfile.class);
                startActivity(intent);
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


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
                date_input.setText(selectedDate);
            }
        }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}