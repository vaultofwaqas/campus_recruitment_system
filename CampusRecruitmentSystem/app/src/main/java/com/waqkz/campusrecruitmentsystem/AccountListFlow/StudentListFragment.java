package com.waqkz.campusrecruitmentsystem.AccountListFlow;

import android.app.ProgressDialog;
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
import com.waqkz.campusrecruitmentsystem.AccountDetailFlow.AccountDetailActivity;
import com.waqkz.campusrecruitmentsystem.AccountInfoFlow.StudentInfo;
import com.waqkz.campusrecruitmentsystem.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends Fragment implements StudentListRecyclerAdapter.itemClickCallback {

    private RecyclerView studentRecyclerView;
    public static StudentListRecyclerAdapter studentListRecyclerAdapter;
    public static ArrayList<StudentInfo> studentArrayList;

    public static ProgressDialog mProgressDialog;

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_list, container, false);

        AccountListActivity.toolBarText.setText(getString(R.string.student_list_info));

        attachingWidgets(rootView);

        studentRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        studentRecyclerView.setLayoutManager(layoutManager);

        mProgressDialog = new ProgressDialog(getActivity());

        studentArrayList = new ArrayList<StudentInfo>();

        listOfStudents();

        studentListRecyclerAdapter = new StudentListRecyclerAdapter(studentArrayList, getActivity());
        studentRecyclerView.setAdapter(studentListRecyclerAdapter);
        studentListRecyclerAdapter.setOnItemClickCallback(this);

        return rootView;
    }

    public void attachingWidgets(View view){

        studentRecyclerView = (RecyclerView) view.findViewById(R.id.student_card_list);
    }

    public static void listOfStudents(){

        if (studentArrayList != null){

            studentArrayList.clear();
        }

        mProgressDialog.setMessage("List of student's loading...");
        mProgressDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child("Campus")
                .child("Student")
                .child("student_user_detail_Info")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            for (DataSnapshot data: dataSnapshot.getChildren()) {

                                StudentInfo studentInfo = data.getValue(StudentInfo.class);

                                studentArrayList.add(studentInfo);

                            }
                            studentListRecyclerAdapter.notifyDataSetChanged();
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        mProgressDialog.dismiss();
                    }
                });
    }

    public static void appliedStudentList(){

        if (studentArrayList != null){

            studentArrayList.clear();
        }

        mProgressDialog.setMessage("List of applied student's loading...");
        mProgressDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child("Campus")
                .child("Company")
                .child("student_user_resume")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            for (DataSnapshot data:
                                    dataSnapshot.getChildren()) {

                                StudentInfo studentInfo = data.getValue(StudentInfo.class);

                                studentArrayList.add(studentInfo);
                            }
                        }

                        studentListRecyclerAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        mProgressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
        intent.putExtra("memberType", AccountListActivity.membershipType);
        intent.putExtra("student_info", studentArrayList.get(position));
        startActivity(intent);
    }
}
