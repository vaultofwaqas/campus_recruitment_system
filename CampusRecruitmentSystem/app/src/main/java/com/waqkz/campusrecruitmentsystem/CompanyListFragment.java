package com.waqkz.campusrecruitmentsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.CompanySignUpForm;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyListFragment extends Fragment {

    private ListView companyListView;
    private CompanyAdapter companyAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    private ArrayList<CompanySignUpForm> companyArrayList;


    public CompanyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_company_list, container, false);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        attachingWidgets(rootView);
        initializingComponents();

        companyArrayList = new ArrayList<CompanySignUpForm>();
        companyAdapter = new CompanyAdapter(getActivity(), companyArrayList);
        companyListView.setAdapter(companyAdapter);

        return rootView;
    }

    public void attachingWidgets(View view){

        companyListView = (ListView) view.findViewById(R.id.company_list);

    }

    public void initializingComponents(){

        databaseReference = mDatabase.child("Campus").child("Company");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                childEventListener = this;

                if (dataSnapshot.getValue() != null){

                    CompanySignUpForm companySignUpForm = dataSnapshot.getValue(CompanySignUpForm.class);

                    companyArrayList.add(new CompanySignUpForm(companySignUpForm.getCompanyUUID(),
                            companySignUpForm.getCompanyID(),
                            companySignUpForm.getCompanyFullname(),
                            companySignUpForm.getCompanyEmail(),
                            companySignUpForm.getCompanyPassword()));

                    companyAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        companyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CompanySignUpForm companySignUpForm = companyArrayList.get(position);

                Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);

                intent.putExtra("STUDENT_UUID", companySignUpForm.getCompanyID());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(childEventListener != null){

            databaseReference.removeEventListener(childEventListener);
        }
    }

}
