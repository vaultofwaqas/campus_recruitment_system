package com.waqkz.campusrecruitmentsystem.AccountCreationModule;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.CampusRecruitmentActivity;
import com.waqkz.campusrecruitmentsystem.R;
import com.waqkz.campusrecruitmentsystem.Utils.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private static final String NAME = "packageName";

    private EditText emailUser;
    private EditText passwordUser;
    private Button signInUserButton;
    private LinearLayout signUpLayout;

    private Boolean mCheckState = false;

    private String mEmail;
    private String mPassword;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    private static String USER_EMAIL = "email";

    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    private SharedPreferences preferences;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mProgressDialog = new ProgressDialog(getActivity());

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        preferences = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        emailUser = (EditText) view.findViewById(R.id.email_sign_in);
        passwordUser = (EditText) view.findViewById(R.id.password_sign_in);
        signInUserButton = (Button) view.findViewById(R.id.sign_in);
        signUpLayout = (LinearLayout) view.findViewById(R.id.sign_up);
    }

    public void initializingComponents(){

        //mFirebaseAuthListener = firebaseAuth -> authenticateListener(firebaseAuth);

        signInUserButton.setOnClickListener(v -> authenticateUser());

        signUpLayout.setOnClickListener(v -> getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ViewPagerSignUpFragment())
                .addToBackStack("signinfragment")
                .commit());
    }

    public void authenticateListener(FirebaseAuth firebaseAuth){

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null && preferences.getString(USER_EMAIL, "") != null) {

            if (currentUser.getEmail().toString().equals(preferences.getString(USER_EMAIL, ""))){

                openCampusRecruitmentActivity();
            }

        }
    }

    public void authenticateUser(){

        mProgressDialog.setMessage("Signing in ...");
        mProgressDialog.show();

        mEmail = emailUser.getText().toString();
        mPassword = passwordUser.getText().toString();

        if (TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(mPassword)) {

            mProgressDialog.dismiss();

            emailUser.setError(getString(R.string.sign_in_email));
            passwordUser.setError(getString(R.string.sign_in_password));

            return;
        }

        preferences.edit().putString(USER_EMAIL, mEmail);

        mFirebaseAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(getActivity(), task -> signInUser(task));

    }

    public void signInUser(Task task){

        if (!task.isSuccessful()){

            if (mPassword.length() < 6){

                mProgressDialog.dismiss();
                passwordUser.setError(getString(R.string.password_error));

            } else {

                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
            }

        } else {

            openCampusRecruitmentActivity();
            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Signed in successfully.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    public void openCampusRecruitmentActivity(){

        Intent intent = new Intent(getActivity(), CampusRecruitmentActivity.class);
        startActivity(intent);
    }

    /*@Override
    public void onResume() {
        super.onResume();

        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mFirebaseAuthListener != null){

            mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
        }
    }*/
}
