package com.waqkz.campusrecruitmentsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.CompanySignUpForm;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.CompanySignUpFragment;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

import static android.content.ContentValues.TAG;

public class CampusRecruitmentActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Boolean CheckStateStudent = false;
    private Boolean CheckStateCompany = false;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_recruitment);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuthListener = firebaseAuth -> initializeUser(firebaseAuth);
    }

    public void initializeUser(FirebaseAuth firebaseAuth){

        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {

            mDatabase.child("Campus").child("Student-Form")
                    .child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null){

                        StudentForm studentForm = dataSnapshot.getValue(StudentForm.class);

                        if (studentForm.getStudentPhoneNumber() != null){

                            CheckStateStudent = true;

                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fragment_container_two, new CompanyListFragment()).commit();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("Campus").child("Company-Form")
                    .child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null){

                        CompanyForm companyForm = dataSnapshot.getValue(CompanyForm.class);

                        if (companyForm.getCompanyPhoneNumber() != null){

                            CheckStateCompany = true;

                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fragment_container_two, new StudentListFragment()).commit();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            mDatabase.child("Campus").child("Student")
                    .child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null){

                        StudentSignUpForm studentSignUpForm = dataSnapshot.getValue(StudentSignUpForm.class);

                        if (currentUser.getUid().equals(studentSignUpForm.getStudentUUID())){

                            if (CheckStateStudent == true){

                                return;
                            }

                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fragment_container_two, new StudentFormFragment()).commit();


                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("Campus").child("Company")
                    .child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null){

                        CompanySignUpForm companySignUpForm = dataSnapshot.getValue(CompanySignUpForm.class);

                        if (currentUser.getUid().equals(companySignUpForm.getCompanyUUID())){

                            if (CheckStateCompany == true){

                                return;
                            }

                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fragment_container_two, new CompanyFormFragment()).commit();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
