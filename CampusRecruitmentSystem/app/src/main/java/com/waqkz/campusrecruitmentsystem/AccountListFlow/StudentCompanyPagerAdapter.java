package com.waqkz.campusrecruitmentsystem.AccountListFlow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.waqkz.campusrecruitmentsystem.R;

/**
 * Created by Adnan Ahmed on 3/8/2017.
 */

public class StudentCompanyPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public StudentCompanyPagerAdapter(Context context, FragmentManager fragmentManager) {
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
