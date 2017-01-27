package com.waqkz.campusrecruitmentsystem;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFormFragment extends Fragment {

    private EditText studentLastSemester;
    private EditText studentCGPA;
    private EditText studentPhoneNumber;
    private EditText studentAcademicHonors;
    private EditText studentCommunityService;
    private EditText studentWorkExperiance;
    private EditText studentHobbies;
    private Button studentSaveButton;

    private String mStudentLastSemester;
    private String mStudentCGPA;
    private String mStudentPhoneNumber;
    private String mStudentAcademicHonors;
    private String mStudentCommunityService;
    private String mStudentWorkExperiance;
    private String mStudentHobbies;

    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    public StudentFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_form, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mProgressDialog = new ProgressDialog(getActivity());

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        studentLastSemester = (EditText) view.findViewById(R.id.student_semester);
        studentCGPA = (EditText) view.findViewById(R.id.student_cgpa);
        studentPhoneNumber = (EditText) view.findViewById(R.id.student_phone);
        studentAcademicHonors = (EditText) view.findViewById(R.id.student_academic_honors);
        studentCommunityService = (EditText) view.findViewById(R.id.community_service);
        studentWorkExperiance = (EditText) view.findViewById(R.id.student_work_experiance);
        studentHobbies = (EditText) view.findViewById(R.id.student_hobbies);
        studentSaveButton= (Button) view.findViewById(R.id.student_save);
    }

    public void initializingComponents(){

        studentSaveButton.setOnClickListener(v -> addingStudentForm());

    }

    public void addingStudentForm(){

        mProgressDialog.setMessage("Adding Form ...");
        mProgressDialog.show();

        mStudentLastSemester = studentLastSemester.getText().toString();
        mStudentCGPA = studentCGPA.getText().toString();
        mStudentPhoneNumber = studentPhoneNumber.getText().toString();
        mStudentAcademicHonors = studentAcademicHonors.getText().toString();
        mStudentCommunityService = studentCommunityService.getText().toString();
        mStudentWorkExperiance = studentWorkExperiance.getText().toString();
        mStudentHobbies = studentHobbies.getText().toString();

        if (TextUtils.isEmpty(mStudentLastSemester)
                && TextUtils.isEmpty(mStudentCGPA)
                && TextUtils.isEmpty(mStudentPhoneNumber)
                && TextUtils.isEmpty(mStudentAcademicHonors)
                && TextUtils.isEmpty(mStudentCommunityService)
                && TextUtils.isEmpty(mStudentWorkExperiance)
                && TextUtils.isEmpty(mStudentHobbies)) {

            mProgressDialog.dismiss();

            studentLastSemester.setError(getString(R.string.last_semester_passed));
            studentCGPA.setError(getString(R.string.cgpa));
            studentPhoneNumber.setError(getString(R.string.phone_number));
            studentAcademicHonors.setError(getString(R.string.academic_honors));
            studentCommunityService.setError(getString(R.string.community_service));
            studentWorkExperiance.setError(getString(R.string.work_experiance));
            studentHobbies.setError(getString(R.string.hobbies));

            return;
        }

        StudentForm studentForm = new StudentForm(studentLastSemester.getText().toString(),
                studentCGPA.getText().toString(),
                studentPhoneNumber.getText().toString(),
                studentAcademicHonors.getText().toString(),
                studentCommunityService.getText().toString(),
                studentWorkExperiance.getText().toString(),
                studentHobbies.getText().toString());

        mDatabase.child("Campus").child("Student-Form").child(currentUser.getUid()).setValue(studentForm);

        mProgressDialog.dismiss();

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_two, new StudentListFragment());
    }
}
