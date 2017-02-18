package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

/**
 * Created by Waqas on 2/18/2017.
 */

public class SignUp {

    private String UUID;
    private String email;
    private String password;

    public SignUp() {
    }

    public SignUp(String UUID, String email, String password) {
        this.UUID = UUID;
        this.email = email;
        this.password = password;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
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
