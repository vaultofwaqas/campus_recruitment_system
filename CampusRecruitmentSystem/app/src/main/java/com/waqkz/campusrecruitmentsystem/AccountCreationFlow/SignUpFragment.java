package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private TextView textError;
    private String mSignUpConfirmPassword;

    private Spinner spinner;

    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;

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
        textError = (TextView) view.findViewById(R.id.spinner_error);
        spinner = (Spinner) view.findViewById(R.id.membershipType);
    }

    public void attachingComponents(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.member_type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Membership Type");
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,
                R.layout.contact_spinner_row_nothing_selected,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                getActivity()));

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

                if (spinner.getSelectedItem() == null){

                    textError.setError(getString(R.string.member_type_not_selected));
                    textError.requestFocus();

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
                                mSignUpPassword,
                                spinner.getSelectedItem().toString());

                        if (!task.isSuccessful()) {

                            Toast.makeText(getActivity(), getString(R.string.auth_failed),
                                    Toast.LENGTH_SHORT).show();

                            mProgressDialog.dismiss();

                        } else {

                            mDatabase.child(getString(R.string.campus))
                                    .child(getString(R.string.user_node))
                                    .child(UUID)
                                    .setValue(signUp);

                            signUpEmail.setText("");
                            signUpPassword.setText("");
                            signUpConfirmPassword.setText("");

                            mProgressDialog.dismiss();

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new SignInFragment())
                                    .commit();

                            Snackbar.make(v, R.string.account_creation_succesful, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
