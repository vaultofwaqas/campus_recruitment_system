package com.waqkz.campusrecruitmentsystem.AccountListFlow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.AccountCreationActivity;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.SignUp;
import com.waqkz.campusrecruitmentsystem.AccountDetailFlow.AccountDetailActivity;
import com.waqkz.campusrecruitmentsystem.NotificationService;
import com.waqkz.campusrecruitmentsystem.R;

public class  AccountListActivity extends AppCompatActivity {

    public static String membershipType;
    public static int menuItemView;

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    public static TextView toolBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list_detail);

        Intent intent = new Intent(this, NotificationService.class);
        this.startService(intent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);

        attachingWidgets();

        Intent intents = getIntent();
        membershipType = intents.getExtras().getString("memberType");

        mAuth = FirebaseAuth.getInstance();

        saveUserPref();

        if (membershipType.equals(getString(R.string.admin_type))){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_list_detail_container, new StudentCompanyPagerFragment())
                    .commit();


        } else if (membershipType.equals(getString(R.string.student_type))){

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
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (membershipType.equals(getString(R.string.student_type))){

            MenuItem registerOne = menu.findItem(R.id.list_of_companies_with_vacancies_available);
            MenuItem registerTwo = menu.findItem(R.id.list_of_companies);
            registerOne.setVisible(true);
            registerTwo.setVisible(true);
        }

        if (membershipType.equals(getString(R.string.company_type))) {

            MenuItem registerOne = menu.findItem(R.id.list_of_applied_students);
            MenuItem registerTwo = menu.findItem(R.id.list_of_students);
            registerOne.setVisible(true);
            registerTwo.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        menuItemView = item.getItemId();

        switch (item.getItemId()) {

            case R.id.list_of_companies:

                CompanyListFragment.listOfCompanies();
                break;

            case R.id.list_of_companies_with_vacancies_available:

                CompanyListFragment.companiesWithVacancies();
                break;

            case R.id.list_of_students:

                StudentListFragment.listOfStudents();
                break;

            case R.id.list_of_applied_students:

                StudentListFragment.appliedStudentList();
                break;

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

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }
}
