package com.waqkz.campusrecruitmentsystem.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.waqkz.campusrecruitmentsystem.AccountCreationModule.CompanySignUpForm;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

/**
 * Created by waqkz on 1/26/17.
 */

public class SharedPref {

    private static String NAME = "packageName";

    private static String STUDENT_UUID = "studentUUID";
    private static String STUDENT_ID = "studentID";
    private static String STUDENT_FULLNAME = "student_fullname";
    private static String STUDENT_EMAIL = "studentEmail";
    private static String STUDENT_PASSWORD = "studentPassword";

    private static String COMPANY_UUID = "studentUUID";
    private static String COMPANY_ID = "studentID";
    private static String COMPANY_FULLNAME = "student_fullname";
    private static String COMPANY_EMAIL = "studentEmail";
    private static String COMPANY_PASSWORD = "studentPassword";

    public static void setCurrentStudent(Context context, StudentSignUpForm studentSignUpForm) {

        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

        preferences.edit().putString(STUDENT_UUID, studentSignUpForm.getStudentUUID());
        preferences.edit().putString(STUDENT_ID, studentSignUpForm.getStudentID()).apply();
        preferences.edit().putString(STUDENT_FULLNAME, studentSignUpForm.getStudentFullname()).apply();
        preferences.edit().putString(STUDENT_EMAIL, studentSignUpForm.getStudentEmail()).apply();
        preferences.edit().putString(STUDENT_PASSWORD, studentSignUpForm.getStudentPassword()).apply();
    }

    public static StudentSignUpForm getCurrentStudent(Context context) {

        StudentSignUpForm studentSignUpForm = new StudentSignUpForm();

        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

        studentSignUpForm.setStudentUUID(preferences.getString(STUDENT_UUID, ""));
        studentSignUpForm.setStudentID(preferences.getString(STUDENT_ID, ""));
        studentSignUpForm.setStudentFullname(preferences.getString(STUDENT_FULLNAME, ""));
        studentSignUpForm.setStudentEmail(preferences.getString(STUDENT_EMAIL, ""));
        studentSignUpForm.setStudentPassword(preferences.getString(STUDENT_PASSWORD, ""));

        return studentSignUpForm;
    }

    public static void setCurrentCompany(Context context, CompanySignUpForm companySignUpForm) {

        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

        preferences.edit().putString(COMPANY_UUID, companySignUpForm.getCompanyUUID());
        preferences.edit().putString(COMPANY_ID, companySignUpForm.getCompanyID()).apply();
        preferences.edit().putString(COMPANY_FULLNAME, companySignUpForm.getCompanyFullname()).apply();
        preferences.edit().putString(COMPANY_EMAIL, companySignUpForm.getCompanyEmail()).apply();
        preferences.edit().putString(COMPANY_PASSWORD, companySignUpForm.getCompanyPassword()).apply();
    }

    public static CompanySignUpForm getCurrentCompany(Context context) {

        CompanySignUpForm companySignUpForm = new CompanySignUpForm();

        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

        companySignUpForm.setCompanyUUID(preferences.getString(COMPANY_UUID, ""));
        companySignUpForm.setCompanyID(preferences.getString(COMPANY_ID, ""));
        companySignUpForm.setCompanyFullname(preferences.getString(COMPANY_FULLNAME, ""));
        companySignUpForm.setCompanyEmail(preferences.getString(COMPANY_EMAIL, ""));
        companySignUpForm.setCompanyPassword(preferences.getString(COMPANY_PASSWORD, ""));

        return companySignUpForm;
    }
}
