package com.waqkz.campusrecruitmentsystem.AccountCreationModule;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waqkz.campusrecruitmentsystem.AccountCreationModule.SignUpPagerAdapter;
import com.waqkz.campusrecruitmentsystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerSignUpFragment extends Fragment {

    private ViewPager signUpViewPager;
    private SignUpPagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    public ViewPagerSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_view_pager_sign_up, container, false);

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        signUpViewPager = (ViewPager) view.findViewById(R.id.signup_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
    }

    public void initializingComponents(){

        pagerAdapter = new SignUpPagerAdapter(getActivity(), getChildFragmentManager());
        signUpViewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(signUpViewPager);
    }
}
