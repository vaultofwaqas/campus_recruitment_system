package com.waqkz.campusrecruitmentsystem;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waqkz.campusrecruitmentsystem.AccountCreationModule.SignUpPagerAdapter;

public class AdminActivity extends AppCompatActivity {

    private ViewPager adminViewPager;
    private SignUpPagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminViewPager = (ViewPager) findViewById(R.id.signup_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        pagerAdapter = new SignUpPagerAdapter(this, getSupportFragmentManager());
        adminViewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(adminViewPager);
    }
}
