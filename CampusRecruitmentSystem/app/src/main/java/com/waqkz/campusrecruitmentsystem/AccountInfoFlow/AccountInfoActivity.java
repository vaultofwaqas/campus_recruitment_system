package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.waqkz.campusrecruitmentsystem.R;

public class AccountInfoActivity extends AppCompatActivity {

    private String membershipType;

    private LinearLayout linearStudentLayout;
    private LinearLayout linearCompanyLayout;

    private ImageView userImageView;

    private EditText studentName;
    private EditText studentID;
    private EditText studentPhoneNumber;
    private EditText studentDateOfBirth;
    private EditText studentMarks;
    private RadioGroup studentRadioGroup;
    private RadioButton studentMaleRadioButton;
    private RadioButton studentFemaleRadioButton;
    private Button studentSaveAndContinue;

    private EditText companyName;
    private EditText companyAddress;
    private EditText companyPhoneNumber;
    private EditText companyWebPage;
    private CheckBox companyVacancyAvailableCheck;
    private Button companySaveAndContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= 21)
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Intent intent = getIntent();
        membershipType = intent.getExtras().getString("memberType");

        attachingWidgets();

        if (membershipType.equals(getString(R.string.student_type))){

            linearStudentLayout.setVisibility(View.VISIBLE);
            linearCompanyLayout.setVisibility(View.GONE);

            userImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_student));

        } else if (membershipType.equals(getString(R.string.company_type))){

            linearStudentLayout.setVisibility(View.GONE);
            linearCompanyLayout.setVisibility(View.VISIBLE);

            userImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_company_image));
        }

        initializingWidgets();
    }

    public void attachingWidgets(){

        linearStudentLayout = (LinearLayout) findViewById(R.id.students_info);
        linearCompanyLayout = (LinearLayout) findViewById(R.id.company_info);

        userImageView = (ImageView) findViewById(R.id.user_default_image);

        studentName = (EditText) findViewById(R.id.student_name);
        studentID = (EditText) findViewById(R.id.student_id);
        studentPhoneNumber = (EditText) findViewById(R.id.student_phone_number);
        studentDateOfBirth = (EditText) findViewById(R.id.student_date_of_birth);
        studentMarks = (EditText) findViewById(R.id.student_marks);
        studentRadioGroup = (RadioGroup) findViewById(R.id.student_radio_group);
        studentMaleRadioButton = (RadioButton) findViewById(R.id.student_male_radio_button);
        studentFemaleRadioButton = (RadioButton) findViewById(R.id.student_female_radio_button);
        studentSaveAndContinue = (Button) findViewById(R.id.student_save_continue);

        companyName = (EditText) findViewById(R.id.company_name);
        companyAddress = (EditText) findViewById(R.id.company_address);
        companyPhoneNumber = (EditText) findViewById(R.id.company_phone_number);
        companyWebPage = (EditText) findViewById(R.id.company_web_page);
        companyVacancyAvailableCheck = (CheckBox) findViewById(R.id.vacancy_check);
        companySaveAndContinue = (Button) findViewById(R.id.company_save_continue);
    }

    public void initializingWidgets(){


    }
}
