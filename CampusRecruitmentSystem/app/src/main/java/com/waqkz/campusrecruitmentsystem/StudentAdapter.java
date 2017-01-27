package com.waqkz.campusrecruitmentsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.waqkz.campusrecruitmentsystem.AccountCreationModule.StudentSignUpForm;

import java.util.ArrayList;

/**
 * Created by waqkz on 1/26/17.
 */

public class StudentAdapter extends ArrayAdapter<StudentSignUpForm> {


    public StudentAdapter(Context context, ArrayList<StudentSignUpForm> studentSignUpForms) {
        super(context, 0, studentSignUpForms);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.student_list, parent, false);
        }

        final StudentSignUpForm studentSignUpForm = getItem(position);

        TextView studentListName = (TextView) listItemView.findViewById(R.id.student_list_name);
        TextView studentListEmail = (TextView) listItemView.findViewById(R.id.student_list_email);

        studentListName.setText(studentSignUpForm.getStudentFullname());
        studentListEmail.setText(studentSignUpForm.getStudentEmail());

        return listItemView;
    }
}
