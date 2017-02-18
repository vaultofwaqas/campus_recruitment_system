package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waqkz.campusrecruitmentsystem.R;

public class AccountInfoActivity extends AppCompatActivity {

    private String memberShipType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        Intent intent = getIntent();
        memberShipType = intent.getExtras().getString("memberType");
    }
}
