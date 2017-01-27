package com.waqkz.campusrecruitmentsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView studentIdDetail;
    private TextView studentFullnameDetail;
    private TextView studentEmail;
    private TextView studentSemesterDetail;
    private TextView studentCGPADetail;
    private TextView studentPhoneDetail;
    private TextView studentAcademicHonors;
    private TextView studentServiceDetail;
    private TextView studentWorkExperiance;
    private TextView studentHobbies;

    private String studentUUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        Intent intent = getIntent();

        studentUUID = intent.getStringExtra("STUDENT_UUID");

        studentIdDetail = (TextView) findViewById(R.id.student_Id_detail);
        studentFullnameDetail = (TextView) findViewById(R.id.student_fullname_detail);
        studentEmail = (TextView) findViewById(R.id.student_email_detail);
        studentSemesterDetail = (TextView) findViewById(R.id.student_semester_detail);
        studentCGPADetail = (TextView) findViewById(R.id.student_cgpa_detail);
        studentPhoneDetail = (TextView) findViewById(R.id.student_phone_detail);
        studentAcademicHonors = (TextView) findViewById(R.id.student_academic_honors);
        studentServiceDetail = (TextView) findViewById(R.id.community_service_detail);
        studentWorkExperiance = (TextView) findViewById(R.id.student_work_experiance_detail);
        studentHobbies = (TextView) findViewById(R.id.student_hobbies_detail);

        FirebaseDatabase.getInstance().getReference()
                .child("Campus").child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot != null){

                    StudentSignUpForm studentSignUpForm = dataSnapshot.getValue(StudentSignUpForm.class);

                    studentIdDetail.setText(studentSignUpForm.getStudentID());
                    studentFullnameDetail.setText(studentSignUpForm.getStudentFullname());
                    studentEmail.setText(studentSignUpForm.getStudentEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference()
                .child("Campus").child("Student-Form")
                .child(studentUUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() != null) {

                    StudentForm studentForm = dataSnapshot.getValue(StudentForm.class);

                    studentSemesterDetail.setText(studentForm.getStudentLastSemester());

                    studentCGPADetail.setText(studentForm.getStudentCGPA());
                    studentPhoneDetail.setText(studentForm.getStudentPhoneNumber());
                    studentAcademicHonors.setText(studentForm.getStudentAcademicHonors());
                    studentServiceDetail.setText(studentForm.getStudentCommunityService());
                    studentWorkExperiance.setText(studentForm.getStudentWorkExperiance());
                    studentHobbies.setText(studentForm.getStudentHobbies());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
