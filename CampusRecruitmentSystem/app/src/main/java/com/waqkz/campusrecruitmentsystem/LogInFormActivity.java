package com.waqkz.campusrecruitmentsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogInFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_form);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new SignInFragment())
                .commit();
    }
}
