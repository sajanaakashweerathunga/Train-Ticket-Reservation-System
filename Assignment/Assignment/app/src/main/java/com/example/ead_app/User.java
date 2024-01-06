package com.example.ead_app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {

    private String UserName;
    private String NIC;
    private String Email;
    private String Mobile;
    private String Password;
    public boolean active = true;

    public User(String userName, String NIC, String email, String mobile, String password) {
        UserName = userName;
        this.NIC = NIC;
        Email = email;
        Mobile = mobile;
        Password = password;
    }

    public User(){

    }

    protected User(Parcel in) {
        UserName = in.readString();
        NIC = in.readString();
        Email = in.readString();
        Mobile = in.readString();
        Password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserName() {
        return UserName;
    }

    public String getNIC() {
        return NIC;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserName);
        dest.writeString(NIC);
        dest.writeString(Email);
        dest.writeString(Mobile);
        dest.writeString(Password);
    }
}
