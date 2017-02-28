package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import com.waqkz.campusrecruitmentsystem.AccountListFlow.AccountListActivity;
import com.waqkz.campusrecruitmentsystem.R;

public class AccountCreationActivity extends AppCompatActivity implements TitleFragment.SendMembershipTypeListener {

    public static boolean checkConnectivity(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private static Context context;
    public static TextView toolBarText;

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        context = this.getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        attachingWidgets();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.prefKey),0);

        mAuth = FirebaseAuth.getInstance();
        String membershipType = sharedPreferences.getString(getResources().getString(R.string.prefType), "");

        if (mAuth.getCurrentUser() != null && !membershipType.equals("")){

            Intent intent = new Intent(this, AccountListActivity.class);
            intent.putExtra("memberType", membershipType);
            startActivity(intent);

            finish();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new TitleFragment())
                .commit();
    }

    @Override
    public void sendMembershipType(String sendMemberType) {

        SignInFragment.membershipType = sendMemberType;
        SignUpFragment.membershipType = sendMemberType;
    }

    public void attachingWidgets(){

        toolBarText = (TextView) findViewById(R.id.title_page);
    }

    public static Context getContext() {
        return context;
    }
}
