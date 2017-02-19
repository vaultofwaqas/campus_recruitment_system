package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.waqkz.campusrecruitmentsystem.R;

public class AccountInfoActivity extends AppCompatActivity {

    private String membershipType;

    private LinearLayout linearStudentLayout;
    private LinearLayout linearCompanyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= 21)
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Intent intent = getIntent();
        membershipType = intent.getExtras().getString("memberType");

        attachingWidgets();

        if (membershipType.equals(getString(R.string.student_type))){

            linearStudentLayout.setVisibility(View.VISIBLE);
            linearCompanyLayout.setVisibility(View.GONE);

        } else if (membershipType.equals(getString(R.string.company_type))){

            linearStudentLayout.setVisibility(View.GONE);
            linearCompanyLayout.setVisibility(View.VISIBLE);
        }
    }

    public void attachingWidgets(){

        linearStudentLayout = (LinearLayout) findViewById(R.id.students_info);
        linearCompanyLayout = (LinearLayout) findViewById(R.id.company_info);
    }
}
