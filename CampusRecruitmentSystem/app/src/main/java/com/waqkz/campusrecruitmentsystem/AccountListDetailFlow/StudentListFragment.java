package com.waqkz.campusrecruitmentsystem.AccountListDetailFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.SignUp;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.CompanyInfo;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.StudentInfo;
import com.waqkz.campusrecruitmentsystem.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends Fragment {

    private RecyclerView studentRecyclerView;
    private StudentListRecyclerAdapter studentListRecyclerAdapter;
    private ArrayList<UserList> studentArrayList;
    private UserList users;

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_list, container, false);

        AccountListDetailActivity.toolBarText.setText(getString(R.string.student_list_info));

        attachingWidgets(rootView);

        studentRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        studentRecyclerView.setLayoutManager(layoutManager);

        studentArrayList = new ArrayList<UserList>();

        attachingComponents();

        studentListRecyclerAdapter = new StudentListRecyclerAdapter(studentArrayList, getActivity());
        studentRecyclerView.setAdapter(studentListRecyclerAdapter);

        return rootView;
    }

    public void attachingWidgets(View view){

        studentRecyclerView = (RecyclerView) view.findViewById(R.id.student_card_list);
    }

    public void attachingComponents(){

        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.campus))
                .child(getString(R.string.student_type))
                .child(getString(R.string.student_login_info))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            for (DataSnapshot data: dataSnapshot.getChildren()) {

                                final SignUp signUp = data.getValue(SignUp.class);

                                FirebaseDatabase.getInstance().getReference()
                                        .child(getString(R.string.campus))
                                        .child(getString(R.string.student_type))
                                        .child(getString(R.string.student_info))
                                        .child(signUp.getUUID())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                if (dataSnapshot.exists()){

                                                    StudentInfo studentInfo = dataSnapshot.getValue(StudentInfo.class);

                                                    users = new UserList(studentInfo.getStudentURL(),
                                                            studentInfo.getStudentName(),
                                                            studentInfo.getStudentID(),
                                                            signUp.getEmail());

                                                    studentArrayList.add(users);
                                                    studentListRecyclerAdapter.notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
