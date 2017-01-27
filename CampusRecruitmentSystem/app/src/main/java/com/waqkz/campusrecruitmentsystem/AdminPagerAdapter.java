package com.waqkz.campusrecruitmentsystem;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.waqkz.campusrecruitmentsystem.AccountCreationModule.CompanySignUpFragment;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpFragment;

/**
 * Created by waqkz on 1/27/17.
 */

public class AdminPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public AdminPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new StudentListFragment();
        } else {
            return new CompanyListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.student_list);
        } else {
            return mContext.getString(R.string.company_list);
        }
    }
}
