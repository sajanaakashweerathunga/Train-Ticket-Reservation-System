package com.example.ead_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TravelApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Reservation";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NIC = "_nic";
    private static final String COLUMN_TRAIN = "_train";
    private static final String COLUMN_START_STATION = "_startStation";
    private static final String COLUMN_END_STATION = "_endStation";
    private static final String COLUMN_DATE = "_date";
    private static final String COLUMN_TIME = "_time";
    private static final String COLUMN_SEATS = "_seats";

    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NIC = "user_nic";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_MOBILE = "user_mobile";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NIC + " TEXT," +COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_MOBILE + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                         " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         COLUMN_NIC + " TEXT, " +
                         COLUMN_TRAIN + " TEXT, " +
                         COLUMN_START_STATION + " TEXT, " +
                         COLUMN_END_STATION + " TEXT, " +
                         COLUMN_DATE + " TEXT, " +
                         COLUMN_TIME + " TEXT, " +
                         COLUMN_SEATS + " INTEGER);";

        db.execSQL(query);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    void addReservation(String nic, String train, String startStation, String endStation, String date, String time, int seats){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NIC, nic);
        cv.put(COLUMN_TRAIN, train);
        cv.put(COLUMN_START_STATION, startStation);
        cv.put(COLUMN_END_STATION, endStation);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_SEATS, seats);
        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1){
            Toast.makeText(context, "Insert Fail", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show();
        }
    }

    //read all reservation
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return  cursor;
    }

    //read all reservation by NIC
    Cursor readDataByNIC(String nic) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NIC + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{nic});
        }
        return cursor;
    }


    //update reservation information
    void updateData(String row_id, String nic, String train, String start, String end, String date, String time, String seats){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NIC, nic);
        cv.put(COLUMN_TRAIN, train);
        cv.put(COLUMN_START_STATION, start);
        cv.put(COLUMN_END_STATION, end);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_SEATS, seats);

//        long results = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
//        if(results == -1){
//            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(context, "Successfully Updated"+ row_id, Toast.LENGTH_SHORT).show();
//        }

        int rowsAffected = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (rowsAffected > 0) {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No rows were updated for " + row_id, Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Error During Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }


//   ---------------------- User Table functions ---------------------------
    public boolean RegisterUser (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {

//            cv.put("UserName", user.getUserName());
//            cv.put("NIC", user.getNIC());
//            cv.put("Email", user.getEmail());
//            cv.put("Mobile", user.getMobile());
//            cv.put("Password", user.getPassword());
            cv.put(COLUMN_USER_NIC, user.getNIC());
            cv.put(COLUMN_USER_NAME, user.getUserName());
            cv.put(COLUMN_USER_EMAIL, user.getEmail());
            cv.put(COLUMN_USER_MOBILE, user.getMobile());
            cv.put(COLUMN_USER_PASSWORD, user.getPassword());


            db.insert(TABLE_USER, null, cv);
            // Query and log the inserted data
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
            if (cursor.moveToFirst()) {
                do {
                    // Log each column's value
                    String name = cursor.getString(0);
                    String nic = cursor.getString(1);
                    String email = cursor.getString(2);
                    String mobile = cursor.getString(3);
                    String password = cursor.getString(4);

                    Log.d("Inserted Data", "Name: " + name + ", NIC: " + nic + ", Email: " + email
                            + ", Mobile: " + mobile + ", Password: " + password);
//                    Toast.makeText(context, "Name" + name + " NIC" + nic, Toast.LENGTH_SHORT).show();

                } while (cursor.moveToNext());
            }
            cursor.close();
            return true;
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public ArrayList<User> LoginUser(String nic, String password) {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Use placeholders to avoid SQL injection
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NIC + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";

            // Execute the query with placeholders
            Cursor cursor = db.rawQuery(query, new String[]{nic, password});

            // Move the cursor to the first row
            if (cursor.moveToFirst()) {
                do {

                    User user = new User();
                    user.setNIC(cursor.getString(0));
                    user.setPassword(cursor.getString(1));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            // Close the cursor
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
        } finally {
            // Close the database
            db.close();
        }

        return userList;
    }

    public User findUserByNIC(String nic) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        try {
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NIC + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{nic});

            if (cursor.moveToFirst()) {
                // If a user with the given NIC is found, create a User object and populate it with data
                String userName = cursor.getString(2);
                String email = cursor.getString(3);
                String mobile = cursor.getString(4);
                String password = cursor.getString(5);

                // Create a User object
                user = new User(userName, nic, email, mobile, password);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return user;
    }

    public boolean updateUserByNIC(String nic, User updatedUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean success = false;

        try {
            // Put the updated values in ContentValues
            contentValues.put(COLUMN_USER_NAME, updatedUser.getUserName());
            contentValues.put(COLUMN_USER_EMAIL, updatedUser.getEmail());
            contentValues.put(COLUMN_USER_MOBILE, updatedUser.getMobile());
            contentValues.put(COLUMN_USER_PASSWORD, updatedUser.getPassword());

            // Update the user record based on NIC
            int rowsAffected = db.update(TABLE_USER, contentValues, COLUMN_USER_NIC + " = ?", new String[]{nic});

            // Check if the update was successful
            if (rowsAffected > 0) {
                Toast.makeText(context, "Update Success!!!", Toast.LENGTH_SHORT).show();
                success = true;
            }else {
                Toast.makeText(context, "Not Updated", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return success;
    }

    public boolean deleteUserByNIC(String nic) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;

        try {
            // Delete the user record based on NIC
            int rowsAffected = db.delete(TABLE_USER, COLUMN_USER_NIC + " = ?", new String[]{nic});

            // Check if the delete was successful
            if (rowsAffected > 0) {
                Toast.makeText(context, "Deactivated Success", Toast.LENGTH_SHORT).show();
                success = true;
            }else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return success;
    }



}
