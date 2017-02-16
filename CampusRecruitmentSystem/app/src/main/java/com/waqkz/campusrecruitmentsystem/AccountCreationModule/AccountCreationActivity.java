package com.waqkz.campusrecruitmentsystem.AccountCreationModule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waqkz.campusrecruitmentsystem.R;

public class AccountCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        /*getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new SignInFragment())
                .commit();*/
    }
}
