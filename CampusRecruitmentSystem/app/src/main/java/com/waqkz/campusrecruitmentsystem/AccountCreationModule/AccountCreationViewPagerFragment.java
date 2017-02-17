package com.waqkz.campusrecruitmentsystem.AccountCreationModule;

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
public class AccountCreationViewPagerFragment extends Fragment {

    private ViewPager accountCreationViewPager;
    private AccountCreationPagerAdapter accountCreationPagerAdapter;
    private TabLayout accountCreationTabLayout;

    public AccountCreationViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_creation_view_pager, container, false);

        attachingWidgets(view);
        initializingComponents();

        return view;
    }

    public void attachingWidgets(View view){

        accountCreationViewPager = (ViewPager) view.findViewById(R.id.account_creation_viewpager);
        accountCreationTabLayout = (TabLayout) view.findViewById(R.id.account_creation_tabs);
    }

    public void initializingComponents(){

        accountCreationPagerAdapter = new AccountCreationPagerAdapter(getActivity(), getChildFragmentManager());
        accountCreationViewPager.setAdapter(accountCreationPagerAdapter);

        accountCreationTabLayout.setupWithViewPager(accountCreationViewPager);
    }
}
