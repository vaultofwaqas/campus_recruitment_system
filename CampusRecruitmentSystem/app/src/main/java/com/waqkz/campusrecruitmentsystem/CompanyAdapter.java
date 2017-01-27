package com.waqkz.campusrecruitmentsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.waqkz.campusrecruitmentsystem.AccountCreationModule.CompanySignUpForm;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

import java.util.ArrayList;

/**
 * Created by waqkz on 1/27/17.
 */

public class CompanyAdapter extends ArrayAdapter<CompanySignUpForm> {

    public CompanyAdapter(Context context, ArrayList<CompanySignUpForm> companyArrayList) {
        super(context, 0, companyArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.company_list, parent, false);
        }

        final CompanySignUpForm companySignUpForm = getItem(position);

        TextView companyListName = (TextView) listItemView.findViewById(R.id.company_list_name);
        TextView companyListEmail = (TextView) listItemView.findViewById(R.id.company_list_email);

        companyListName.setText(companySignUpForm.getCompanyFullname());
        companyListEmail.setText(companySignUpForm.getCompanyEmail());

        return listItemView;
    }
}
