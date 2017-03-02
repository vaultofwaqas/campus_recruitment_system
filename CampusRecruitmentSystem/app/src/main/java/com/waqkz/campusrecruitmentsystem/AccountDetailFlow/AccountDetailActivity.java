package com.waqkz.campusrecruitmentsystem.AccountDetailFlow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.google.firebase.auth.FirebaseAuth;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.AccountCreationActivity;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.UserList;
import com.waqkz.campusrecruitmentsystem.R;

public class AccountDetailActivity extends AppCompatActivity{

    private FloatingActionButton detailSignOut;

    private ImageView userDetailImage;

    private LinearLayout studentDetailLinearLayout;
    private TextView studentDetailName;
    private TextView studentDetailEmail;
    private TextView studentDetailID;
    private TextView studentDetailDateOfBirth;
    private TextView studentDetailPhoneNumber;
    private TextView studentDetailMarks;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;

    private LinearLayout companyDetailLinearLayout;
    private TextView companyDetailName;
    private TextView companyDetailEmail;
    private TextView companyDetailPhoneNumber;
    private TextView companyDetailAddress;
    private TextView companyDetailWebPage;
    private LinearLayout companyVacancyAvailableCheck;

    private String membershipType;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    public static UserList userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Intent intent = getIntent();
        membershipType = intent.getExtras().getString("memberType");
        userList = (UserList) intent.getSerializableExtra("user_info");

        mAuth = FirebaseAuth.getInstance();

        saveUserPref();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Typeface font = Typer.set(getApplicationContext()).getFont(Font.ROBOTO_MEDIUM);
        collapsingToolbar.setExpandedTitleTypeface(font);

        attachingWidgets();

        if (membershipType.equals(getString(R.string.student_type))){

            collapsingToolbar.setTitle(getString(R.string.company_detail_info));

            Glide.with(getApplicationContext()).load(userList.getUserImageURL()).asBitmap()
                    .error(R.drawable.default_company_image).centerCrop().into(userDetailImage);

            companyDetailLinearLayout.setVisibility(View.VISIBLE);
            studentDetailLinearLayout.setVisibility(View.GONE);

            companyDetailName.setText(userList.getUserName());
            companyDetailEmail.setText(userList.getUserEmail());
            companyDetailPhoneNumber.setText(userList.getUserPhoneNumber());
            companyDetailAddress.setText(userList.getUserCompanyAddress());
            companyDetailWebPage.setText(userList.getUserCompanyWebPage());

            if (userList.getUserCompanyVacancyAvailableCheck() == false){

                companyVacancyAvailableCheck.setVisibility(View.GONE);

            } else if (userList.getUserCompanyVacancyAvailableCheck() == true){

                companyVacancyAvailableCheck.setVisibility(View.VISIBLE);
            }

        } else if (membershipType.equals(getString(R.string.company_type))){

            collapsingToolbar.setTitle(getString(R.string.student_detail_info));

            Glide.with(getApplicationContext()).load(userList.getUserImageURL()).asBitmap()
                    .error(R.drawable.default_student).centerCrop().into(userDetailImage);

            companyDetailLinearLayout.setVisibility(View.GONE);
            studentDetailLinearLayout.setVisibility(View.VISIBLE);

            studentDetailName.setText(userList.getUserName());
            studentDetailEmail.setText(userList.getUserEmail());
            studentDetailID.setText(userList.getUserStudentID());
            studentDetailDateOfBirth.setText(userList.getUserStudentDateOfBirth());
            studentDetailPhoneNumber.setText(userList.getUserPhoneNumber());
            studentDetailMarks.setText(userList.getUserStudentMarks());

            if (userList.getUserStudentGender().equals(getString(R.string.student_male))){

                maleRadioButton.setChecked(true);
                maleRadioButton.setEnabled(true);

                femaleRadioButton.setChecked(false);
                femaleRadioButton.setEnabled(false);

            } else if (userList.getUserStudentGender().equals(getString(R.string.student_female))){

                maleRadioButton.setChecked(false);
                maleRadioButton.setEnabled(false);

                femaleRadioButton.setChecked(true);
                femaleRadioButton.setEnabled(true);
            }
        }

        initializingWidgets();
    }

    public void attachingWidgets(){

        detailSignOut = (FloatingActionButton) findViewById(R.id.detail_sign_out);

        userDetailImage = (ImageView) findViewById(R.id.user_detail_default_image);

        studentDetailLinearLayout = (LinearLayout) findViewById(R.id.student_detail_info_id);
        studentDetailName = (TextView) findViewById(R.id.student_detail_name);
        studentDetailEmail = (TextView) findViewById(R.id.student_detail_email);
        studentDetailID = (TextView) findViewById(R.id.student_detail_id);
        studentDetailDateOfBirth = (TextView) findViewById(R.id.student_detail_date_of_birth);
        studentDetailPhoneNumber = (TextView) findViewById(R.id.student_detail_phone_number);
        studentDetailMarks = (TextView) findViewById(R.id.student_detail_marks);
        maleRadioButton = (RadioButton) findViewById(R.id.student_detail_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.student_detail_female);

        companyDetailLinearLayout = (LinearLayout) findViewById(R.id.company_detail_info_id);
        companyDetailName = (TextView) findViewById(R.id.company_detail_name);
        companyDetailEmail = (TextView) findViewById(R.id.company_detail_email);
        companyDetailPhoneNumber = (TextView) findViewById(R.id.company_detail_phone_number);
        companyDetailAddress = (TextView) findViewById(R.id.company_detail_address);
        companyDetailWebPage = (TextView) findViewById(R.id.company_detail_web_page);
        companyVacancyAvailableCheck = (LinearLayout) findViewById(R.id.vacancy_available_id);
    }

    public void initializingWidgets(){

        detailSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                sharedPreferences.edit().remove(getResources().getString(R.string.prefType)).apply();

                Intent intent = new Intent(AccountDetailActivity.this, AccountCreationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();
            }
        });
    }

    public void saveUserPref(){

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.prefKey),0);
        sharedPreferences.edit().putString(getResources().getString(R.string.prefType),membershipType).apply();
    }
}
