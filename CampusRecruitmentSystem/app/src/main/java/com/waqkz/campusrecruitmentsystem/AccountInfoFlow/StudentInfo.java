package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

/**
 * Created by Waqas on 2/25/2017.
 */

public class StudentInfo {

    String studentName;
    String studentID;
    String studentPhoneNumber;
    String studentDateOfBirth;
    String studentMarks;
    String studentGender;
    String studentURL;

    public StudentInfo() {
    }

    public StudentInfo(String studentName, String studentID, String studentPhoneNumber, String studentDateOfBirth, String studentMarks, String studentGender, String studentURL) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentDateOfBirth = studentDateOfBirth;
        this.studentMarks = studentMarks;
        this.studentGender = studentGender;
        this.studentURL = studentURL;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public void setStudentPhoneNumber(String studentPhoneNumber) {
        this.studentPhoneNumber = studentPhoneNumber;
    }

    public String getStudentDateOfBirth() {
        return studentDateOfBirth;
    }

    public void setStudentDateOfBirth(String studentDateOfBirth) {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentURL() {
        return studentURL;
    }

    public void setStudentURL(String studentURL) {
        this.studentURL = studentURL;
    }
}
