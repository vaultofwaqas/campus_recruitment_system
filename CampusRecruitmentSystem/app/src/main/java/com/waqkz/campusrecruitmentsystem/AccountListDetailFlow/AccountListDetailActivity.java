package com.waqkz.campusrecruitmentsystem.AccountListDetailFlow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.AccountCreationActivity;
import com.waqkz.campusrecruitmentsystem.R;

public class AccountListDetailActivity extends AppCompatActivity {

    private String membershipType;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    public static TextView toolBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);

        attachingWidgets();

        Intent intent = getIntent();
        membershipType = intent.getExtras().getString("memberType");

        mAuth = FirebaseAuth.getInstance();

        saveUserPref();

        if (membershipType.equals(getString(R.string.student_type))){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_list_detail_container, new CompanyListFragment())
                    .commit();

        } else if (membershipType.equals(getString(R.string.company_type))){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_list_detail_container, new StudentListFragment())
                    .commit();
        }
    }

    public void attachingWidgets(){

        toolBarText = (TextView) findViewById(R.id.title_page);
    }

    public void saveUserPref(){

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.prefKey),0);
        sharedPreferences.edit().putString(getResources().getString(R.string.prefType),membershipType).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:

                mAuth.signOut();
                sharedPreferences.edit().remove(getResources().getString(R.string.prefType)).apply();

                Intent intent = new Intent(this, AccountCreationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
