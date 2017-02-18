package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.waqkz.campusrecruitmentsystem.R;

public class AccountInfoActivity extends AppCompatActivity {

    private String membershipType;

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
    }
}
