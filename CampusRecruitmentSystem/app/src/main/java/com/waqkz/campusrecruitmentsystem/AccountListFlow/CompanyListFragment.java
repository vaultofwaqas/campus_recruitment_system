package com.waqkz.campusrecruitmentsystem.AccountListFlow;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.SignUp;
import com.waqkz.campusrecruitmentsystem.AccountDetailFlow.AccountDetailActivity;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.CompanyInfo;
import com.waqkz.campusrecruitmentsystem.NotificationService;
import com.waqkz.campusrecruitmentsystem.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyListFragment extends Fragment implements CompanyListRecyclerAdapter.itemClickCallback{

    private RecyclerView companyRecyclerView;
    public static CompanyListRecyclerAdapter companyListRecyclerAdapter;
    public static ArrayList<CompanyInfo> companyArrayList;

    public static ProgressDialog mProgressDialog;

    public CompanyListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_list, container, false);

        if (AccountListActivity.membershipType.equals(getString(R.string.admin_type))){

            AccountListActivity.toolBarText.setText(getString(R.string.admin_dashboard));

        } else {

            AccountListActivity.toolBarText.setText(getString(R.string.company_dashboard));
        }

        attachingWidgets(rootView);

        companyRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        companyRecyclerView.setLayoutManager(layoutManager);

        mProgressDialog = new ProgressDialog(getActivity());

        companyArrayList = new ArrayList<CompanyInfo>();

        listOfCompanies();

        companyListRecyclerAdapter = new CompanyListRecyclerAdapter(companyArrayList, getActivity());
        companyRecyclerView.setAdapter(companyListRecyclerAdapter);
        companyListRecyclerAdapter.setOnItemClickCallback(this);

        return rootView;
    }

    public void attachingWidgets(View view){

        companyRecyclerView = (RecyclerView) view.findViewById(R.id.company_card_list);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
        intent.putExtra("memberType", AccountListActivity.membershipType);
        intent.putExtra("company_info", companyArrayList.get(position));
        startActivity(intent);
    }

    public static void listOfCompanies(){

        if (companyArrayList != null){

            companyArrayList.clear();
        }

        mProgressDialog.setMessage("List of companies loading...");
        mProgressDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child("Campus")
                .child("Company")
                .child("company_user_detail_info")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            for (DataSnapshot data: dataSnapshot.getChildren()) {

                                CompanyInfo companyInfo = data.getValue(CompanyInfo.class);

                                companyArrayList.add(companyInfo);

                            }
                            companyListRecyclerAdapter.notifyDataSetChanged();

                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        mProgressDialog.dismiss();
                    }
                });

        mProgressDialog.dismiss();
    }

    public static void companiesWithVacancies(){

        if (companyArrayList != null){

            companyArrayList.clear();
        }

        mProgressDialog.setMessage("Companies loading with vacancies available ...");
        mProgressDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child("Campus")
                .child("Company")
                .child("company_user_detail_info")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            for (DataSnapshot data:
                                    dataSnapshot.getChildren()){

                                CompanyInfo companyInfo = data.getValue(CompanyInfo.class);

                                if (companyInfo.getCompanyVacancyAvailableCheck() == true){

                                    companyArrayList.add(companyInfo);
                                }
                            }
                        }

                        companyListRecyclerAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        mProgressDialog.dismiss();
                    }
                });
    }
}
