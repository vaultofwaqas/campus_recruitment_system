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
import com.waqkz.campusrecruitmentsystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanySignUpFragment extends Fragment {

    private EditText companyId;
    private EditText companyFullName;
    private EditText companyEmail;
    private EditText companyPassword;
    private Button companySignUpButton;

    private String mCompanyId;
    private String mCompanyFullName;
    private String mCompanyEmail;
    private String mCompanyPassword;

    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    private CompanySignUpForm companySignUpForm;


    public CompanySignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_sign_up, container, false);

        mProgressDialog = new ProgressDialog(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        companyId = (EditText) view.findViewById(R.id.company_id);
        companyFullName = (EditText) view.findViewById(R.id.company_full_name_text);
        companyEmail = (EditText) view.findViewById(R.id.company_email_text);
        companyPassword = (EditText) view.findViewById(R.id.company_password_text);
        companySignUpButton = (Button) view.findViewById(R.id.company_sign_up_button);
    }

    public void initializingComponents(){

        companySignUpButton.setOnClickListener(v -> authenticateCompany());

    }

    public void authenticateCompany(){

        mProgressDialog.setMessage("Signing up ...");
        mProgressDialog.show();

        mCompanyId = companyId.getText().toString();
        mCompanyFullName = companyFullName.getText().toString();
        mCompanyEmail = companyEmail.getText().toString();
        mCompanyPassword = companyPassword.getText().toString();

        if (TextUtils.isEmpty(mCompanyId)
                && TextUtils.isEmpty(mCompanyFullName)
                && TextUtils.isEmpty(mCompanyEmail)
                && TextUtils.isEmpty(mCompanyPassword)) {

            mProgressDialog.dismiss();

            companyId.setError(getString(R.string.company_id));
            companyFullName.setError(getString(R.string.company_full_name));
            companyEmail.setError(getString(R.string.company_email));
            companyPassword.setError(getString(R.string.company_password));

            return;
        }

        if (companyPassword.length() < 6) {

            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), getString(R.string.password_error), Toast.LENGTH_SHORT).show();

            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mCompanyEmail,
                mCompanyPassword).addOnCompleteListener(getActivity(), task -> authComplete(task));
    }

    public void authComplete(Task task){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        companySignUpForm = new CompanySignUpForm(user.getUid(),
                mCompanyId,
                mCompanyFullName,
                mCompanyEmail,
                mCompanyPassword);

        if (!task.isSuccessful()) {

            Toast.makeText(getActivity(), getString(R.string.auth_failed) + ": " + task.getException(),
                    Toast.LENGTH_SHORT).show();

            mProgressDialog.dismiss();

        } else {

            mDatabase.child("Campus").child("Company")
                    .child(companySignUpForm.getCompanyUUID())
                    .setValue(companySignUpForm);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SignInFragment())
                    .commit();

            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Signed up successfully.", Toast.LENGTH_SHORT).show();
        }
    }

}
