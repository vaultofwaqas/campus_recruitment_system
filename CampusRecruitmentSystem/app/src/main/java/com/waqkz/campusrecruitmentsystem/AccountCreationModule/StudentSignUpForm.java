package com.waqkz.campusrecruitmentsystem.AccountCreationModule;

/**
 * Created by waqkz on 1/25/17.
 */

public class StudentSignUpForm {

    public String studentUUID;
    public String studentID;
    public String studentFullname;
    public String studentEmail;
    public String studentPassword;

    public StudentSignUpForm() {
    }

    public StudentSignUpForm(String studentUUID, String studentID, String studentFullname, String studentEmail, String studentPassword) {
        this.studentUUID = studentUUID;
        this.studentID = studentID;
        this.studentFullname = studentFullname;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
    }

    public String getStudentUUID() {
        return studentUUID;
    }

    public void setStudentUUID(String studentUUID) {
        this.studentUUID = studentUUID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentFullname() {
        return studentFullname;
    }

    public void setStudentFullname(String studentFullname) {
        this.studentFullname = studentFullname;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }
}
