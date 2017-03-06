package com.waqkz.campusrecruitmentsystem.AccountDetailFlow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.AccountCreationActivity;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.CompanyInfo;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.StudentInfo;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.StudentListFragment;
import com.waqkz.campusrecruitmentsystem.R;

public class AccountDetailActivity extends AppCompatActivity{

    public static TextView toolBarText;

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
    private LinearLayout companyVacancyAvailableCheck;
    private LinearLayout companyVacancyCancellationCheck;
    private LinearLayout noCompanyVacancy;

    private LinearLayout companyDetailLinearLayout;
    private TextView companyDetailName;
    private TextView companyDetailEmail;
    private TextView companyDetailPhoneNumber;
    private TextView companyDetailAddress;
    private TextView companyDetailWebPage;
    private LinearLayout sentResumeUI;
    private LinearLayout acceptStudentResume;
    private LinearLayout cancelStudentResume;

    private ProgressDialog mProgressDialog;

    private String membershipType;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    public static CompanyInfo companyInfo;
    public static StudentInfo studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Intent intent = getIntent();
        membershipType = intent.getExtras().getString("memberType");
        companyInfo = (CompanyInfo) intent.getSerializableExtra("company_info");
        studentInfo = (StudentInfo) intent.getSerializableExtra("student_info");

        mAuth = FirebaseAuth.getInstance();

        saveUserPref();

        mProgressDialog = new ProgressDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        attachingWidgets();

        if (membershipType.equals(getString(R.string.student_type))){

            collapsingToolbar.setTitle(getString(R.string.company_detail_info));

            Glide.with(getApplicationContext()).load(companyInfo.getCompanyURL()).asBitmap()
                    .error(R.drawable.default_company_image).centerCrop().into(userDetailImage);

            companyDetailLinearLayout.setVisibility(View.VISIBLE);
            studentDetailLinearLayout.setVisibility(View.GONE);

            companyDetailName.setText(companyInfo.getCompanyName());
            companyDetailEmail.setText(companyInfo.getCompanyEmail());
            companyDetailPhoneNumber.setText(companyInfo.getCompanyPhoneNumber());
            companyDetailAddress.setText(companyInfo.getCompanyAddress());
            companyDetailWebPage.setText(companyInfo.getCompanyWebPage());

            if (companyInfo.getCompanyVacancyAvailableCheck() == false){

                noCompanyVacancy.setVisibility(View.VISIBLE);
                companyVacancyAvailableCheck.setVisibility(View.GONE);
                companyVacancyCancellationCheck.setVisibility(View.GONE);

            } else if (companyInfo.getCompanyVacancyAvailableCheck() == true){

                noCompanyVacancy.setVisibility(View.GONE);
                companyVacancyAvailableCheck.setVisibility(View.VISIBLE);
                companyVacancyCancellationCheck.setVisibility(View.GONE);
            }

            FirebaseDatabase.getInstance().getReference()
                    .child(getString(R.string.campus))
                    .child(getString(R.string.company_type))
                    .child(getString(R.string.student_resume))
                    .child(companyInfo.getCompanyUUID())
                    .child(mAuth.getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()){

                                noCompanyVacancy.setVisibility(View.GONE);
                                companyVacancyAvailableCheck.setVisibility(View.GONE);
                                companyVacancyCancellationCheck.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            companyVacancyAvailableCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    mProgressDialog.setMessage("Your resume is being sent.");
                    mProgressDialog.show();

                    FirebaseDatabase.getInstance().getReference()
                            .child(getString(R.string.campus))
                            .child(getString(R.string.student_type))
                            .child(getString(R.string.student_info))
                            .child(mAuth.getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()){

                                        FirebaseDatabase.getInstance().getReference()
                                                .child(getString(R.string.campus))
                                                .child(getString(R.string.company_type))
                                                .child(getString(R.string.student_resume))
                                                .child(companyInfo.getCompanyUUID())
                                                .child(mAuth.getCurrentUser().getUid())
                                                .setValue(dataSnapshot.getValue());

                                        mProgressDialog.dismiss();
                                        noCompanyVacancy.setVisibility(View.GONE);
                                        companyVacancyAvailableCheck.setVisibility(View.GONE);
                                        companyVacancyCancellationCheck.setVisibility(View.VISIBLE);
                                        Snackbar.make(v, getString(R.string.resume_sent_successfully), Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    mProgressDialog.dismiss();

                                    Snackbar.make(v, getString(R.string.resume_sending_failed), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            });
                }
            });

            companyVacancyCancellationCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference()
                            .child(getString(R.string.campus))
                            .child(getString(R.string.company_type))
                            .child(getString(R.string.student_resume))
                            .child(companyInfo.getCompanyUUID())
                            .child(mAuth.getCurrentUser().getUid())
                            .setValue(null);

                    noCompanyVacancy.setVisibility(View.GONE);
                    companyVacancyAvailableCheck.setVisibility(View.VISIBLE);
                    companyVacancyCancellationCheck.setVisibility(View.GONE);

                    Snackbar.make(v, getString(R.string.resume_cancelled_successfully), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });

        } else if (membershipType.equals(getString(R.string.company_type))){

            collapsingToolbar.setTitle(getString(R.string.student_detail_info));

            Glide.with(getApplicationContext()).load(studentInfo.getStudentURL()).asBitmap()
                    .error(R.drawable.default_student).centerCrop().into(userDetailImage);

            companyDetailLinearLayout.setVisibility(View.GONE);
            studentDetailLinearLayout.setVisibility(View.VISIBLE);

            studentDetailName.setText(studentInfo.getStudentName());
            studentDetailEmail.setText(studentInfo.getStudentEmail());
            studentDetailID.setText(studentInfo.getStudentID());
            studentDetailDateOfBirth.setText(studentInfo.getStudentDateOfBirth());
            studentDetailPhoneNumber.setText(studentInfo.getStudentPhoneNumber());
            studentDetailMarks.setText(studentInfo.getStudentMarks());

            if (studentInfo.getStudentGender().equals(getString(R.string.student_male))){

                maleRadioButton.setChecked(true);
                maleRadioButton.setEnabled(true);

                femaleRadioButton.setChecked(false);
                femaleRadioButton.setEnabled(false);

            } else if (studentInfo.getStudentGender().equals(getString(R.string.student_female))){

                maleRadioButton.setChecked(false);
                maleRadioButton.setEnabled(false);

                femaleRadioButton.setChecked(true);
                femaleRadioButton.setEnabled(true);
            }

            FirebaseDatabase.getInstance().getReference()
                    .child(getString(R.string.campus))
                    .child(getString(R.string.company_type))
                    .child(getString(R.string.student_resume))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(studentInfo.getStudentUUID())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()){

                                cancelStudentResume.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            cancelStudentResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference()
                            .child(getString(R.string.campus))
                            .child(getString(R.string.company_type))
                            .child(getString(R.string.student_resume))
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(studentInfo.getStudentUUID())
                            .setValue(null);

                    StudentListFragment.appliedStudentList();

                    finish();
                }
            });
        }

        initializingWidgets();
    }

    public void attachingWidgets(){

        toolBarText = (TextView) findViewById(R.id.title_page);

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
        companyVacancyAvailableCheck = (LinearLayout) findViewById(R.id.vacancy_available_id);
        companyVacancyCancellationCheck = (LinearLayout) findViewById(R.id.vacancy_available_cancellation);
        noCompanyVacancy = (LinearLayout) findViewById(R.id.no_vacancy);

        companyDetailLinearLayout = (LinearLayout) findViewById(R.id.company_detail_info_id);
        companyDetailName = (TextView) findViewById(R.id.company_detail_name);
        companyDetailEmail = (TextView) findViewById(R.id.company_detail_email);
        companyDetailPhoneNumber = (TextView) findViewById(R.id.company_detail_phone_number);
        companyDetailAddress = (TextView) findViewById(R.id.company_detail_address);
        companyDetailWebPage = (TextView) findViewById(R.id.company_detail_web_page);
        cancelStudentResume = (LinearLayout) findViewById(R.id.student_resume_cancellation);
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
