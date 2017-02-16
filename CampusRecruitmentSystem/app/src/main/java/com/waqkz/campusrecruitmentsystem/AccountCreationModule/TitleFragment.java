package com.waqkz.campusrecruitmentsystem.AccountCreationModule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.waqkz.campusrecruitmentsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {

    private Button adminLoginButton;
    private Button studenLogintButton;
    private Button companyLoginButton;

    public TitleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_title, container, false);

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        adminLoginButton = (Button) view.findViewById(R.id.admin_login);
        studenLogintButton = (Button) view.findViewById(R.id.student_login);
        companyLoginButton = (Button) view.findViewById(R.id.company_login);
    }

    public void initializingComponents(){

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFragment(getString(R.string.admin_type));
            }
        });

        studenLogintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFragment(getString(R.string.student_type));
            }
        });

        companyLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFragment(getString(R.string.company_type));
            }
        });
    }

    public void openFragment(String memberType){

        SignInFragment signInFragment = new SignInFragment();
        Bundle bundleArgs = new Bundle();
        bundleArgs.getString("check", memberType);
        signInFragment.setArguments(bundleArgs);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, signInFragment)
                .addToBackStack("TitleFragment")
                .commit();
    }
}
