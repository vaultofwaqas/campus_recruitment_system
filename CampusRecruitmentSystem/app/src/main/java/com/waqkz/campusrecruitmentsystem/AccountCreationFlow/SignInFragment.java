package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.AccountListActivity;
import com.waqkz.campusrecruitmentsystem.R;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.AccountInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText emailSignIn;
    private EditText passwordSignIn;
    private Button userSignInButton;
    private LinearLayout goToSignUpFragment;

    private String mEmailSignIn;
    private String mPasswordSignIn;
    private String mEmailPathString;

    private String loginInfoType;
    private String infoType;
    private String accountInfo;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    private String membershipType;

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
        goToSignUpFragment = (LinearLayout) view.findViewById(R.id.go_to_user_sign_up);
    }

    public void attachingComponents(){

        goToSignUpFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment())
                        .addToBackStack("SignInFragment")
                        .commit();
            }
        });

        userSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

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

                if (mPasswordSignIn.length() < 6) {

                    mProgressDialog.dismiss();
                    passwordSignIn.setError(getString(R.string.password_error));

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

                                        mProgressDialog.dismiss();

                                        Toast.makeText(getActivity(), getString(R.string.auth_failed),
                                                Toast.LENGTH_LONG).show();Toast.makeText(getActivity(), getString(R.string.auth_failed),
                                                Toast.LENGTH_LONG).show();

                                    } else {

                                        FirebaseDatabase.getInstance().getReference()
                                                .child(getString(R.string.campus))
                                                .child(getString(R.string.user_node))
                                                .child(mAuth.getCurrentUser().getUid())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        if (dataSnapshot.exists()){

                                                            SignUp signUp = dataSnapshot.getValue(SignUp.class);

                                                            membershipType = signUp.getMemberType();

                                                            if (signUp.getMemberType().equals(getString(R.string.student_type))){

                                                                infoType = getString(R.string.student_info);
                                                                accountInfo = getString(R.string.student_id);

                                                            } else if (signUp.getMemberType().equals(getString(R.string.company_type))){

                                                                infoType = getString(R.string.company_info);
                                                                accountInfo = getString(R.string.company_name);

                                                            } else if (membershipType.equals(getString(R.string.admin_type))){

                                                                loginInfoType = getString(R.string.admin_login_info);
                                                            }

                                                            if (signUp.getMemberType().equals(getString(R.string.admin_type))){

                                                                openAccountListDetailActivity(v);

                                                            } else {

                                                                FirebaseDatabase.getInstance().getReference()
                                                                        .child(getString(R.string.campus))
                                                                        .child(signUp.getMemberType())
                                                                        .child(infoType)
                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                        .child(accountInfo).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                                        if (dataSnapshot.exists()){

                                                                            openAccountListDetailActivity(v);

                                                                        } else {

                                                                            openAccountInfoActivity(v);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            }
                                                        } else {

                                                            mProgressDialog.dismiss();
                                                            mAuth.signOut();
                                                            Snackbar.make(v, getString(R.string.user_not_found), Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

    public void openAccountInfoActivity(View v){

        mProgressDialog.dismiss();
        Snackbar.make(v, "Sign In successful.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        Intent intent = new Intent(getActivity(), AccountInfoActivity.class);
        intent.putExtra("memberType", membershipType);
        startActivity(intent);

        getActivity().finish();
    }

    public void openAccountListDetailActivity(View v){

        mProgressDialog.dismiss();
        Snackbar.make(v, "Sign In successful.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        Intent intent = new Intent(getActivity(), AccountListActivity.class);
        intent.putExtra("memberType", membershipType);
        startActivity(intent);

        getActivity().finish();
    }

}
