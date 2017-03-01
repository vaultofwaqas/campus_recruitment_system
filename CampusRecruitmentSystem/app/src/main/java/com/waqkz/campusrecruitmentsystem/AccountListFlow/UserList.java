package com.waqkz.campusrecruitmentsystem.AccountListFlow;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Waqas on 2/27/2017.
 */

public class UserList implements Serializable{

    private String userUUID;
    private String userEmail;
    private String userName;
    private String userStudentID;
    private String userPhoneNumber;
    private String userStudentMarks;
    private String userStudentDateOfBirth;
    private String userStudentGender;
    private String userCompanyAddress;
    private String userCompanyWebPage;
    private Boolean userCompanyVacancyAvailableCheck;
    private String userImageURL;

    public UserList() {
    }

    public UserList(String userUUID,
                    String userEmail,
                    String userName,
                    String userCompanyAddress,
                    String userPhoneNumber,
                    String userImageURL,
                    Boolean userCompanyVacancyAvailableCheck,
                    String userCompanyWebPage) {

        this.userUUID = userUUID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCompanyAddress = userCompanyAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.userImageURL = userImageURL;
        this.userCompanyVacancyAvailableCheck = userCompanyVacancyAvailableCheck;
        this.userCompanyWebPage = userCompanyWebPage;
    }

    public UserList(String userUUID,
                    String userEmail,
                    String userStudentGender,
                    String userStudentMarks,
                    String userName,
                    String userStudentID,
                    String userPhoneNumber,
                    String userImageURL,
                    String userStudentDateOfBirth) {

        this.userUUID = userUUID;
        this.userEmail = userEmail;
        this.userStudentGender = userStudentGender;
        this.userStudentMarks = userStudentMarks;
        this.userName = userName;
        this.userStudentID = userStudentID;
        this.userPhoneNumber = userPhoneNumber;
        this.userImageURL = userImageURL;
        this.userStudentDateOfBirth = userStudentDateOfBirth;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStudentID() {
        return userStudentID;
    }

    public void setUserStudentID(String userStudentID) {
        this.userStudentID = userStudentID;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserStudentMarks() {
        return userStudentMarks;
    }

    public void setUserStudentMarks(String userStudentMarks) {
        this.userStudentMarks = userStudentMarks;
    }

    public String getUserStudentGender() {
        return userStudentGender;
    }

    public void setUserStudentGender(String userStudentGender) {
        this.userStudentGender = userStudentGender;
    }

    public String getUserCompanyAddress() {
        return userCompanyAddress;
    }

    public void setUserCompanyAddress(String userCompanyAddress) {
        this.userCompanyAddress = userCompanyAddress;
    }

    public String getUserStudentDateOfBirth() {
        return userStudentDateOfBirth;
    }

    public void setUserStudentDateOfBirth(String userStudentDateOfBirth) {
        this.userStudentDateOfBirth = userStudentDateOfBirth;
    }

    public String getUserCompanyWebPage() {
        return userCompanyWebPage;
    }

    public void setUserCompanyWebPage(String userCompanyWebPage) {
        this.userCompanyWebPage = userCompanyWebPage;
    }

    public Boolean getUserCompanyVacancyAvailableCheck() {
        return userCompanyVacancyAvailableCheck;
    }

    public void setUserCompanyVacancyAvailableCheck(Boolean userCompanyVacancyAvailableCheck) {
        this.userCompanyVacancyAvailableCheck = userCompanyVacancyAvailableCheck;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }
}
