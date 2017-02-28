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
public class StudentDetailFragment extends Fragment {

    public StudentDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_detail, container, false);

        AccountDetailActivity.toolBarText.setText(getString(R.string.student_detail_info));

        return rootView;
    }
}
