package com.waqkz.campusrecruitmentsystem.AccountCreationModule;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private SendMembershipTypeListener sendMembershipTypeListener;

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

                sendMembershipTypeListener.sendMembershipType(getString(R.string.admin_type));

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SignInFragment())
                        .addToBackStack("TitleFragment")
                        .commit();
            }
        });

        studenLogintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMembershipTypeListener.sendMembershipType(getString(R.string.student_type));

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AccountCreationViewPagerFragment())
                        .addToBackStack("TitleFragment")
                        .commit();
            }
        });

        companyLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMembershipTypeListener.sendMembershipType(getString(R.string.company_type));

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AccountCreationViewPagerFragment())
                        .addToBackStack("TitleFragment")
                        .commit();
            }
        });
    }

    public interface SendMembershipTypeListener{

        public void sendMembershipType(String sendMemberType);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {

            sendMembershipTypeListener = (SendMembershipTypeListener) context;

        } catch (Exception e){

            e.printStackTrace();
        }
    }
}
