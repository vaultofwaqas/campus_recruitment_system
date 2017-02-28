package com.waqkz.campusrecruitmentsystem.AccountDetailFlow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waqkz.campusrecruitmentsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyDetailFragment extends Fragment {

    public CompanyDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_detail, container, false);

        AccountDetailActivity.toolBarText.setText(getString(R.string.company_detail_info));

        return rootView;
    }
}
