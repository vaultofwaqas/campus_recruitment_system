package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.R;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.AccountInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText emailSignIn;
    private EditText passwordSignIn;
    private Button userSignInButton;

    private String mEmailSignIn;
    private String mPasswordSignIn;
    private String mEmailPathString;

    private String loginInfoType;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    public static String membershipType;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(getActivity());

        attachingWidgets(rootView);
        attachingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        emailSignIn = (EditText) view.findViewById(R.id.user_email_sign_in);
        passwordSignIn = (EditText) view.findViewById(R.id.user_password_sign_in);
        userSignInButton = (Button) view.findViewById(R.id.user_sign_in);
    }

    public void attachingComponents(){

        userSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmailSignIn = emailSignIn.getText().toString();
                mPasswordSignIn = passwordSignIn.getText().toString();

                if (TextUtils.isEmpty(mEmailSignIn) && TextUtils.isEmpty(mPasswordSignIn)) {

                    emailSignIn.setError(getString(R.string.enter_email));
                    passwordSignIn.setError(getString(R.string.enter_password));

                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailSignIn).matches()){

                    emailSignIn.setError(getString(R.string.invalid_email));

                    return;
                }

                try {

                    mProgressDialog.setMessage("Signing in ...");
                    mProgressDialog.show();

                    mAuth.signInWithEmailAndPassword(mEmailSignIn, mPasswordSignIn)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {

                                        if (mPasswordSignIn.length() < 6) {

                                            mProgressDialog.dismiss();
                                            passwordSignIn.setError(getString(R.string.password_error));

                                        } else {

                                            mProgressDialog.dismiss();
                                            Toast.makeText(getActivity(), getString(R.string.auth_failed),
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    } else {

                                        if (membershipType.equals(getString(R.string.student_type))){

                                            loginInfoType = getString(R.string.student_login_info);

                                        } else if (membershipType.equals(getString(R.string.student_type))){

                                            loginInfoType = getString(R.string.company_login_info);

                                        } else if (membershipType.equals(getString(R.string.admin_type))){

                                            loginInfoType = getString(R.string.admin_login_info);
                                        }

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Campus")
                                                .child(membershipType)
                                                .child(loginInfoType)
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                if (dataSnapshot.exists()){

                                                    if (membershipType.equals(getString(R.string.admin_type))){

                                                    } else {

                                                        accountInfoActivity();
                                                    }

                                                    mProgressDialog.dismiss();
                                                    Toast.makeText(getActivity(), "Sign In successful.", Toast.LENGTH_SHORT).show();
                                                    getActivity().finish();
                                                } else {

                                                    mProgressDialog.dismiss();
                                                    mAuth.signOut();
                                                    Toast.makeText(getActivity(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            });

                } catch (Exception ex){

                    ex.printStackTrace();
                }
            }
        });

        emailSignIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){

                    String emailText = ((EditText)v).getText().toString().trim();

                    if (TextUtils.isEmpty(emailText)){

                        emailSignIn.setError(getString(R.string.enter_email));
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){

                        emailSignIn.setError(getString(R.string.invalid_email));
                    }
                }

            }
        });
    }

    public void accountInfoActivity(){

        Intent intent = new Intent(getActivity(), AccountInfoActivity.class);
        intent.putExtra("memberType", membershipType);
        startActivity(intent);
    }
}
