package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

/**
 * Created by Waqas on 2/25/2017.
 */

public class CompanyInfo {

    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String companyWebPage;
    private Boolean companyVacancyAvailableCheck;
    private String companyURL;

    public CompanyInfo() {
    }

    public CompanyInfo(String companyName,
                       String companyAddress,
                       String companyPhoneNumber,
                       String companyWebPage,
                       Boolean companyVacancyAvailableCheck,
                       String companyURL) {

        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyWebPage = companyWebPage;
        this.companyVacancyAvailableCheck = companyVacancyAvailableCheck;
        this.companyURL = companyURL;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Boolean getCompanyVacancyAvailableCheck() {
        return companyVacancyAvailableCheck;
    }

    public void setCompanyVacancyAvailableCheck(Boolean companyVacancyAvailableCheck) {
        this.companyVacancyAvailableCheck = companyVacancyAvailableCheck;
    }

    public String getCompanyURL() {
        return companyURL;
    }

    public void setCompanyURL(String companyURL) {
        this.companyURL = companyURL;
    }
}
