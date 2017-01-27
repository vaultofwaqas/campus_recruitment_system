package com.waqkz.campusrecruitmentsystem;

import android.widget.Button;
import android.widget.EditText;

/**
 * Created by waqkz on 1/26/17.
 */

public class CompanyForm {

    private String companyAddress;
    private String companyPhoneNumber;
    private String companyWebPage;
    private String companyVacancies;

    public CompanyForm() {
    }

    public CompanyForm(String companyAddress, String companyPhoneNumber, String companyWebPage, String companyVacancies) {
        this.companyAddress = companyAddress;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyWebPage = companyWebPage;
        this.companyVacancies = companyVacancies;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebPage() {
        return companyWebPage;
    }

    public void setCompanyWebPage(String companyWebPage) {
        this.companyWebPage = companyWebPage;
    }

    public String getCompanyVacancies() {
        return companyVacancies;
    }

    public void setCompanyVacancies(String companyVacancies) {
        this.companyVacancies = companyVacancies;
    }
}