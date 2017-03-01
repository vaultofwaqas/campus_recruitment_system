package com.waqkz.campusrecruitmentsystem.AccountListFlow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.waqkz.campusrecruitmentsystem.R;

import java.util.ArrayList;

/**
 * Created by Waqas on 2/27/2017.
 */

public class StudentListRecyclerAdapter extends RecyclerView.Adapter<StudentListRecyclerAdapter.StudentListHolder> {

    private ArrayList<UserList> studentListArrayList;
    private LayoutInflater inflater;

    private itemClickCallback itemClickCallback;

    public interface itemClickCallback {

        void onItemClick(int position);
    }

    public void setOnItemClickCallback(final itemClickCallback itemClickCallback){

        this.itemClickCallback = itemClickCallback;
    }

    public StudentListRecyclerAdapter(ArrayList<UserList> studentListData, Context context) {

        studentListArrayList = studentListData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public StudentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_card_items, parent, false);

        return new StudentListHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentListHolder holder, int position) {

        UserList userList = studentListArrayList.get(position);

        Glide.with(inflater.getContext()).load(userList.getUserImageURL()).asBitmap()
                .error(R.drawable.default_student).centerCrop().into(holder.studentImage);

        holder.studentName.setText(userList.getUserName());
        holder.studentID.setText(userList.getUserStudentID());
        holder.studentEmail.setText(userList.getUserEmail());

    }

    @Override
    public int getItemCount() {

        return studentListArrayList.size();
    }

    class StudentListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView studentImage;
        private TextView studentName;
        private TextView studentID;
        private TextView studentEmail;

        private View itemContainer;

        public StudentListHolder(View itemView) {
            super(itemView);

            studentImage = (ImageView) itemView.findViewById(R.id.user_image);
            studentName = (TextView) itemView.findViewById(R.id.user_list_name);
            studentID = (TextView) itemView.findViewById(R.id.user_list_id);
            studentEmail = (TextView) itemView.findViewById(R.id.user_list_email);
            itemContainer = itemView.findViewById(R.id.card_view);

            itemContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            itemClickCallback.onItemClick(getAdapterPosition());
        }
    }
}
