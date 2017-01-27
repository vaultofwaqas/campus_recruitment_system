package com.waqkz.campusrecruitmentsystem.AccountCreationModule;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.SignInFragment;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;
import com.waqkz.campusrecruitmentsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSignUpFragment extends Fragment {

    private EditText studentId;
    private EditText studentFullName;
    private EditText studentEmail;
    private EditText studentPassword;
    private Button studentSignUpButton;

    private String mStudentId;
    private String mStudentFullName;
    private String mStudentEmail;
    private String mStudentPassword;

    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    private StudentSignUpForm studentSignUpForm;

    public StudentSignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_sign_up, container, false);

        mProgressDialog = new ProgressDialog(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        studentId = (EditText) view.findViewById(R.id.student_id);
        studentFullName = (EditText) view.findViewById(R.id.student_full_name_text);
        studentEmail = (EditText) view.findViewById(R.id.student_email_text);
        studentPassword = (EditText) view.findViewById(R.id.student_password_text);
        studentSignUpButton = (Button) view.findViewById(R.id.student_sign_up_button);
    }

    public void initializingComponents(){

        studentSignUpButton.setOnClickListener(v -> authenticateStudent());

    }

    public void authenticateStudent(){

        mProgressDialog.setMessage("Signing up ...");
        mProgressDialog.show();

        mStudentId = studentId.getText().toString();
        mStudentFullName = studentFullName.getText().toString();
        mStudentEmail = studentEmail.getText().toString();
        mStudentPassword = studentPassword.getText().toString();

        if (TextUtils.isEmpty(mStudentId)
                && TextUtils.isEmpty(mStudentFullName)
                && TextUtils.isEmpty(mStudentEmail)
                && TextUtils.isEmpty(mStudentPassword)) {

            mProgressDialog.dismiss();

            studentId.setError(getString(R.string.company_id));
            studentFullName.setError(getString(R.string.company_full_name));
            studentEmail.setError(getString(R.string.company_email));
            studentPassword.setError(getString(R.string.company_password));

            return;
        }

        if (mStudentPassword.length() < 6) {

            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), getString(R.string.password_error), Toast.LENGTH_SHORT).show();

            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mStudentEmail,
               mStudentPassword).addOnCompleteListener(getActivity(), task -> authComplete(task));

    }

    public void authComplete(Task task){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        studentSignUpForm = new StudentSignUpForm(user.getUid(),
                mStudentId,
                mStudentFullName,
                mStudentEmail,
                mStudentPassword);

        if (!task.isSuccessful()) {

            Toast.makeText(getActivity(), getString(R.string.auth_failed) + ": " + task.getException(),
                    Toast.LENGTH_SHORT).show();

            mProgressDialog.dismiss();
        } else {

            mDatabase.child("Campus").child("Student")
                    .child(studentSignUpForm.getStudentUUID())
                    .setValue(studentSignUpForm);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SignInFragment())
                    .commit();

            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Signed up successfully.", Toast.LENGTH_SHORT).show();
        }
    }
}
