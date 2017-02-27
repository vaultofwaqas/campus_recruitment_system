package com.waqkz.campusrecruitmentsystem.AccountListDetailFlow;

/**
 * Created by Waqas on 2/27/2017.
 */

public class UserList {

    private String userImageURL;
    private String userName;
    private String userID;
    private String userEmail;

    public UserList() {
    }

    public UserList(String userImageURL, String userName, String userID, String userEmail) {
        this.userImageURL = userImageURL;
        this.userName = userName;
        this.userID = userID;
        this.userEmail = userEmail;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
