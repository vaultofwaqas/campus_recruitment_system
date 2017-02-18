package com.waqkz.campusrecruitmentsystem.AccountCreationFlow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.waqkz.campusrecruitmentsystem.R;

/**
 * Created by Waqas on 2/17/2017.
 */

public class AccountCreationPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public AccountCreationPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return new SignInFragment();
        } else {

            return new SignUpFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {

            return mContext.getString(R.string.user_sign_in);
        } else {

            return mContext.getString(R.string.user_sign_up);
        }
    }


}
