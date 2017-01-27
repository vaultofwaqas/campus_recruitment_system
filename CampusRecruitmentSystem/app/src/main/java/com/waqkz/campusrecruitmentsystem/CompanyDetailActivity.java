package com.waqkz.campusrecruitmentsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CompanyDetailActivity extends AppCompatActivity {

    private TextView studentIdDetail;
    private TextView studentFullnameDetail;
    private TextView studentEmail;
    private TextView studentSemesterDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
    }
}
