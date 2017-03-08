package com.waqkz.campusrecruitmentsystem.AccountListFlow;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waqkz.campusrecruitmentsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentCompanyPagerFragment extends Fragment {

    private ViewPager studentCompanyViewPager;
    private StudentCompanyPagerAdapter studentCompanyPagerAdapter;
    private TabLayout studentCompanyTabLayout;

    public StudentCompanyPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_company_pager, container, false);

        attachingWidgets(rootView);
        initializingWidgets();

        return rootView;
    }

    public void attachingWidgets(View view){

        studentCompanyViewPager = (ViewPager) view.findViewById(R.id.student_company_viewpager);
        studentCompanyTabLayout = (TabLayout) view.findViewById(R.id.tabs);
    }

    public void initializingWidgets(){

        studentCompanyPagerAdapter = new StudentCompanyPagerAdapter(getActivity(), getChildFragmentManager());
        studentCompanyViewPager.setAdapter(studentCompanyPagerAdapter);

        studentCompanyTabLayout.setupWithViewPager(studentCompanyViewPager);
    }
}
