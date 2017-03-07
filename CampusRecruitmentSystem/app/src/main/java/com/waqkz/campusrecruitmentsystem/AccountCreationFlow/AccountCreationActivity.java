package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import com.waqkz.campusrecruitmentsystem.AccountDetailFlow.AccountDetailActivity;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.AccountListActivity;
import com.waqkz.campusrecruitmentsystem.NotificationService;
import com.waqkz.campusrecruitmentsystem.R;

public class AccountCreationActivity extends AppCompatActivity {

    public static boolean checkConnectivity(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private static Context context;

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        context = this.getApplicationContext();

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
                .add(R.id.fragment_container, new SignInFragment())
                .commit();
    }

    public static Context getContext() {
        return context;
    }
}
