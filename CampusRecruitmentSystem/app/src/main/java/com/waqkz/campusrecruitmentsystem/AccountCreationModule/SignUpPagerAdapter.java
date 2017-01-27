package com.waqkz.campusrecruitmentsystem.AccountCreationModule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.waqkz.campusrecruitmentsystem.R;

/**
 * Created by waqkz on 1/25/17.
 */

public class SignUpPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SignUpPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new StudentSignUpFragment();
        } else {
            return new CompanySignUpFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.student_sign_up);
        } else {
            return mContext.getString(R.string.company_sign_up);
        }
    }
}
