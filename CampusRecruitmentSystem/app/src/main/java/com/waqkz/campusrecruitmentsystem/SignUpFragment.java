package com.waqkz.campusrecruitmentsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText userId;
    private EditText fullName;
    private EditText email;
    private EditText password;
    private Button signUpButton;


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        attachingWidgets(rootView);
        attachingListeners();

        return rootView;
    }

    public void attachingWidgets(View view){

        userId = (EditText) view.findViewById(R.id.user_id);
        fullName = (EditText) view.findViewById(R.id.full_name_text);
        email = (EditText) view.findViewById(R.id.email_text);
        password = (EditText) view.findViewById(R.id.password_text);
        signUpButton = (Button) view.findViewById(R.id.sign_up_button);
    }

    public void  attachingListeners(){


    }
}
