package com.waqkz.campusrecruitmentsystem.AccountCreationModule;

/**
 * Created by waqkz on 1/25/17.
 */

public class CompanySignUpForm {

    private String companyUUID;
    private String companyID;
    private String companyFullname;
    private String companyEmail;
    private String companyPassword;

    public CompanySignUpForm() {
    }

    public CompanySignUpForm(String companyUUID, String companyID, String companyFullname, String companyEmail, String companyPassword) {
        this.companyUUID = companyUUID;
        this.companyID = companyID;
        this.companyFullname = companyFullname;
        this.companyEmail = companyEmail;
        this.companyPassword = companyPassword;
    }

    public String getCompanyUUID() {
        return companyUUID;
    }

    public void setCompanyUUID(String companyUUID) {
        this.companyUUID = companyUUID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyFullname() {
        return companyFullname;
    }

    public void setCompanyFullname(String companyFullname) {
        this.companyFullname = companyFullname;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }
}
