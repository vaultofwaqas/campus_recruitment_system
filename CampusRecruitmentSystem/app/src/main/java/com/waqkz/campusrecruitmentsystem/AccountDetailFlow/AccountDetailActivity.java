package com.waqkz.campusrecruitmentsystem.AccountDetailFlow;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.AccountCreationActivity;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.AccountInfoActivity;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.CompanyInfo;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.StudentInfo;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.AccountListActivity;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.CompanyListFragment;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.StudentListFragment;
import com.waqkz.campusrecruitmentsystem.R;

import java.util.HashMap;

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
    private LinearLayout resumeAccepted;

    private LinearLayout companyDetailLinearLayout;
    private TextView companyDetailName;
    private TextView companyDetailEmail;
    private TextView companyDetailPhoneNumber;
    private TextView companyDetailAddress;
    private TextView companyDetailWebPage;
    private LinearLayout sentResumeUI;
    private LinearLayout acceptStudentResume;
    private LinearLayout cancelStudentResume;
    private LinearLayout resumeAcceptedUI;

    private LinearLayout adminStudentRecordUI;
    private TextView editStudentRecord;
    private TextView deleteStudentRecord;

    private LinearLayout adminCompanyRecordUI;
    private TextView editCompanyRecord;
    private TextView deleteCompanyRecord;

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

        if (companyInfo != null){

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

            if (membershipType.equals(getString(R.string.admin_type))){

                noCompanyVacancy.setVisibility(View.GONE);
                companyVacancyAvailableCheck.setVisibility(View.GONE);
                companyVacancyCancellationCheck.setVisibility(View.GONE);
                resumeAccepted.setVisibility(View.GONE);

                editCompanyRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(AccountDetailActivity.this, AccountInfoActivity.class);
                        intent.putExtra("companysInfo", companyInfo);
                        startActivity(intent);
                    }
                });

                deleteCompanyRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProgressDialog.setMessage("Deleting Company Record . . .");
                        mProgressDialog.show();

                        FirebaseDatabase.getInstance().getReference()
                                .child(getString(R.string.campus))
                                .child(getString(R.string.company_type))
                                .child(getString(R.string.company_info))
                                .child(companyInfo.getCompanyUUID()).removeValue();

                        mProgressDialog.dismiss();

                        Snackbar.make(v, getString(R.string.company_record_deletion_succesfull), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        CompanyListFragment.listOfCompanies();

                        finish();
                    }
                });

                return;
            }

            if (membershipType.equals(getString(R.string.admin_type))) {

                adminCompanyRecordUI.setVisibility(View.VISIBLE);
            } else {

                adminCompanyRecordUI.setVisibility(View.GONE);
            }

            if (companyInfo.getCompanyVacancyAvailableCheck() == false){

                noCompanyVacancy.setVisibility(View.VISIBLE);
                companyVacancyAvailableCheck.setVisibility(View.GONE);
                companyVacancyCancellationCheck.setVisibility(View.GONE);
                resumeAccepted.setVisibility(View.GONE);

            } else if (companyInfo.getCompanyVacancyAvailableCheck() == true){

                noCompanyVacancy.setVisibility(View.GONE);
                companyVacancyAvailableCheck.setVisibility(View.VISIBLE);
                companyVacancyCancellationCheck.setVisibility(View.GONE);
                resumeAccepted.setVisibility(View.GONE);
            }

            FirebaseDatabase.getInstance().getReference()
                    .child(getString(R.string.campus))
                    .child(getString(R.string.company_type))
                    .child(getString(R.string.company_info))
                    .child(companyInfo.getCompanyUUID())
                    .child(getString(R.string.accepted_student_application))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()){

                                noCompanyVacancy.setVisibility(View.GONE);
                                companyVacancyAvailableCheck.setVisibility(View.GONE);
                                companyVacancyCancellationCheck.setVisibility(View.GONE);
                                resumeAccepted.setVisibility(View.VISIBLE);
                            } else {

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
                                                    resumeAccepted.setVisibility(View.GONE);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

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

        } else if (studentInfo != null){

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

            if (membershipType.equals(getString(R.string.admin_type))) {

                adminStudentRecordUI.setVisibility(View.VISIBLE);

                editStudentRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(AccountDetailActivity.this, AccountInfoActivity.class);
                        intent.putExtra("studentsInfo", studentInfo);
                        startActivity(intent);
                    }
                });

                deleteStudentRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProgressDialog.setMessage("Deleting Student Record . . .");
                        mProgressDialog.show();

                        FirebaseDatabase.getInstance().getReference()
                                .child(getString(R.string.campus))
                                .child(getString(R.string.student_type))
                                .child(getString(R.string.student_info))
                                .child(studentInfo.getStudentUUID()).removeValue();

                        mProgressDialog.dismiss();

                        Snackbar.make(v, getString(R.string.company_record_deletion_succesfull), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        StudentListFragment.listOfStudents();

                        finish();
                    }
                });

            } else {

                adminStudentRecordUI.setVisibility(View.GONE);
            }

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

            if (membershipType.equals(getString(R.string.admin_type))) {

                return;
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

                                sentResumeUI.setVisibility(View.VISIBLE);
                                resumeAcceptedUI.setVisibility(View.GONE);
                            } else {

                                sentResumeUI.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            acceptStudentResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{studentInfo.getStudentEmail()});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    FirebaseDatabase.getInstance().getReference()
                            .child(getString(R.string.campus))
                            .child(getString(R.string.company_type))
                            .child(getString(R.string.company_info))
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {

                                        String UUID = studentInfo.getStudentUUID();

                                        CompanyInfo companyInfo = dataSnapshot.getValue(CompanyInfo.class);

                                        NotificationMessage notificationMessage = new NotificationMessage("Your application for " + companyInfo.getCompanyName()
                                                + " has been accepted, and will be contacted shortly.",  FirebaseDatabase.getInstance().getReference()
                                                .child(getString(R.string.campus))
                                                .child(getString(R.string.student_type))
                                                .child(getString(R.string.notification))
                                                .child(UUID).push().getKey(), UUID);

                                        FirebaseDatabase.getInstance().getReference()
                                                .child(getString(R.string.campus))
                                                .child(getString(R.string.student_type))
                                                .child(getString(R.string.notification))
                                                .child(UUID)
                                                .child(notificationMessage.getPushId())
                                                .setValue(notificationMessage);

                                        FirebaseDatabase.getInstance().getReference()
                                                .child(getString(R.string.campus))
                                                .child(getString(R.string.company_type))
                                                .child(getString(R.string.company_info))
                                                .child(companyInfo.getCompanyUUID())
                                                .child(getString(R.string.accepted_student_application))
                                                .child(studentInfo.getStudentUUID())
                                                .setValue(studentInfo);

                                        FirebaseDatabase.getInstance().getReference()
                                                .child(getString(R.string.campus))
                                                .child(getString(R.string.company_type))
                                                .child(getString(R.string.student_resume))
                                                .child(companyInfo.getCompanyUUID())
                                                .child(studentInfo.getStudentUUID()).removeValue();

                                        sentResumeUI.setVisibility(View.GONE);
                                        resumeAcceptedUI.setVisibility(View.VISIBLE);

                                        Toast.makeText(AccountDetailActivity.this, getString(R.string.application_accepted_succesfully),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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

                    if (AccountListActivity.menuItemView == R.id.list_of_students) {

                        StudentListFragment.listOfStudents();

                    } else if (AccountListActivity.menuItemView == R.id.list_of_applied_students){

                        StudentListFragment.appliedStudentList();
                    }

                    finish();

                    Toast.makeText(AccountDetailActivity.this, getString(R.string.application_rejected),
                            Toast.LENGTH_LONG).show();
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
        sentResumeUI = (LinearLayout) findViewById(R.id.resume_sent_ui);
        acceptStudentResume = (LinearLayout) findViewById(R.id.student_resume_accept);
        cancelStudentResume = (LinearLayout) findViewById(R.id.student_resume_cancellation);
        resumeAccepted = (LinearLayout) findViewById(R.id.resume_accepted);

        companyDetailLinearLayout = (LinearLayout) findViewById(R.id.company_detail_info_id);
        companyDetailName = (TextView) findViewById(R.id.company_detail_name);
        companyDetailEmail = (TextView) findViewById(R.id.company_detail_email);
        companyDetailPhoneNumber = (TextView) findViewById(R.id.company_detail_phone_number);
        companyDetailAddress = (TextView) findViewById(R.id.company_detail_address);
        companyDetailWebPage = (TextView) findViewById(R.id.company_detail_web_page);
        companyVacancyAvailableCheck = (LinearLayout) findViewById(R.id.vacancy_available_id);
        companyVacancyCancellationCheck = (LinearLayout) findViewById(R.id.vacancy_available_cancellation);
        noCompanyVacancy = (LinearLayout) findViewById(R.id.no_vacancy);
        resumeAcceptedUI = (LinearLayout) findViewById(R.id.resume_accepted_ui);

        editStudentRecord = (TextView) findViewById(R.id.edit_student_record);
        deleteStudentRecord = (TextView) findViewById(R.id.delete_student_record);
        adminStudentRecordUI = (LinearLayout) findViewById(R.id.admin_student_record_ui);

        editCompanyRecord = (TextView) findViewById(R.id.edit_company_record);
        deleteCompanyRecord = (TextView) findViewById(R.id.delete_company_record);
        adminCompanyRecordUI = (LinearLayout) findViewById(R.id.admin_company_record_ui);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
