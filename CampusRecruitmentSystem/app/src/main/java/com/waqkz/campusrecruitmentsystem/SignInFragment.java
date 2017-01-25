package com.waqkz.campusrecruitmentsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText emailUser;
    private EditText passwordUser;
    private Button signInUser;
    private LinearLayout signUpLayout;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        attachingWidgets(rootView);
        attachingListeners();

        return rootView;
    }

    public void attachingWidgets(View view){

        emailUser = (EditText) view.findViewById(R.id.email_edit_text);
        passwordUser = (EditText) view.findViewById(R.id.password_edit_text);
        signInUser = (Button) view.findViewById(R.id.sign_in);
        signUpLayout = (LinearLayout) view.findViewById(R.id.sign_up);
    }

    public void attachingListeners(){

        signUpLayout.setOnClickListener(v -> getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SignUpFragment())
                .addToBackStack("signinfragment")
                .commit());
    }
}
