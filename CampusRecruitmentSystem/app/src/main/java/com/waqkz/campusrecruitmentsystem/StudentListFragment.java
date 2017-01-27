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
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends Fragment {

    private ListView studentListView;
    private StudentAdapter studentAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    private ArrayList<StudentSignUpForm> studentArrayList;


    public StudentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_list, container, false);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        attachingWidgets(rootView);
        initializingComponents();

        studentArrayList = new ArrayList<StudentSignUpForm>();
        studentAdapter = new StudentAdapter(getActivity(), studentArrayList);
        studentListView.setAdapter(studentAdapter);

        return rootView;
    }

    public void attachingWidgets(View view){

        studentListView = (ListView) view.findViewById(R.id.student_list);

    }

    public void initializingComponents(){

        databaseReference = mDatabase.child("Campus").child("Student");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                childEventListener = this;

                if (dataSnapshot.getValue() != null){

                    StudentSignUpForm studentSignUpForm = dataSnapshot.getValue(StudentSignUpForm.class);

                    studentArrayList.add(new StudentSignUpForm(studentSignUpForm.getStudentUUID(),
                            studentSignUpForm.getStudentID(),
                            studentSignUpForm.getStudentFullname(),
                            studentSignUpForm.getStudentEmail(),
                            studentSignUpForm.getStudentPassword()));

                    studentAdapter.notifyDataSetChanged();

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

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                StudentSignUpForm studentSignUpForm = studentArrayList.get(position);

                Intent intent = new Intent(getActivity(), StudentDetailActivity.class);

                intent.putExtra("STUDENT_UUID", studentSignUpForm.getStudentUUID());
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
