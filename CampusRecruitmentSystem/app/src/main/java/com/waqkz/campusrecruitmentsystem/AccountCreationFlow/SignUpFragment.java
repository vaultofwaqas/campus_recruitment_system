package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.waqkz.campusrecruitmentsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public static String membershipType;

    private EditText signUpEmail;
    private EditText signUpPassword;
    private EditText signUpConfirmPassword;
    private Button signUpButton;

    private String mSignUpEmail;
    private String mSignUpPassword;
    private String mSignUpConfirmPassword;

    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;

    private AccountCreationPagerAdapter viewpager;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        attachingWidgets(rootView);
        attachingComponents();

        mProgressDialog = new ProgressDialog(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return rootView;
    }

    public void attachingWidgets(View view){

        signUpEmail = (EditText) view.findViewById(R.id.user_email_sign_up);
        signUpPassword = (EditText) view.findViewById(R.id.user_password_sign_up);
        signUpConfirmPassword = (EditText) view.findViewById(R.id.user_password_confirm_sign_up);
        signUpButton = (Button) view.findViewById(R.id.user_signup);
    }

    public void attachingComponents(){

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                boolean check = false;

                mSignUpEmail = signUpEmail.getText().toString();
                mSignUpPassword = signUpPassword.getText().toString();
                mSignUpConfirmPassword = signUpConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(mSignUpEmail)
                        && TextUtils.isEmpty(mSignUpPassword)
                        && TextUtils.isEmpty(mSignUpConfirmPassword)) {

                    signUpEmail.setError(getString(R.string.enter_email));
                    signUpPassword.setError(getString(R.string.enter_password));
                    signUpConfirmPassword.setError(getString(R.string.confirm_password));

                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mSignUpEmail).matches()){

                    signUpEmail.setError(getString(R.string.invalid_email));

                    check = true;
                }

                if (mSignUpPassword.length() < 6) {

                    signUpPassword.setError(getString(R.string.password_error));

                    check = true;
                }

                if (!mSignUpPassword.equals(mSignUpConfirmPassword)){

                    signUpPassword.setError(getString(R.string.password_not_matches));
                    signUpConfirmPassword.setError(getString(R.string.password_not_matches));

                    check = true;
                }

                if (check){

                    return;
                }

                mProgressDialog.setMessage("Signing up ...");
                mProgressDialog.show();

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mSignUpEmail,
                        mSignUpPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        String UUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        SignUp signUp = new SignUp(UUID,
                                mSignUpEmail,
                                mSignUpPassword);

                        if (!task.isSuccessful()) {

                            Toast.makeText(getActivity(), getString(R.string.auth_failed),
                                    Toast.LENGTH_SHORT).show();

                            mProgressDialog.dismiss();

                        } else {

                            if (membershipType.equals(getString(R.string.student_type))){

                                mDatabase.child(getString(R.string.campus))
                                        .child(membershipType)
                                        .child(getString(R.string.student_login_info))
                                        .child(UUID)
                                        .setValue(signUp);

                            } else if (membershipType.equals(getString(R.string.company_type))){

                                mDatabase.child(getString(R.string.campus))
                                        .child(membershipType)
                                        .child(getString(R.string.company_login_info))
                                        .child(UUID)
                                        .setValue(signUp);
                            }

                            signUpEmail.setText("");
                            signUpPassword.setText("");
                            signUpConfirmPassword.setText("");

                            AccountCreationViewPagerFragment.accountCreationViewPager.setCurrentItem(0);

                            mProgressDialog.dismiss();

                            Snackbar.make(v, R.string.account_creation_succesful, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                });
            }
        });

        signUpEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){

                    String emailText = ((EditText)v).getText().toString().trim();

                    if (TextUtils.isEmpty(emailText)){

                        signUpEmail.setError(getString(R.string.enter_email));
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){

                        signUpEmail.setError(getString(R.string.invalid_email));
                    }
                }

            }
        });
    }
}
