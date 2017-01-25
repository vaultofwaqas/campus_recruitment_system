package com.waqkz.campusrecruitmentsystem;

/**
 * Created by waqkz on 1/25/17.
 */

public class LogInForm {

    public String UUID;
    public String userID;
    public String fullname;
    public String email;
    public String password;

    public LogInForm() {
    }

    public LogInForm(String UUID, String userID, String fullname, String email, String password) {
        this.UUID = UUID;
        this.userID = userID;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
