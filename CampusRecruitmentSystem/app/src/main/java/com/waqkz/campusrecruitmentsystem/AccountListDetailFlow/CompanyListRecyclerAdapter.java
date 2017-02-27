package com.waqkz.campusrecruitmentsystem.AccountListDetailFlow;

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

public class CompanyListRecyclerAdapter extends RecyclerView.Adapter<CompanyListRecyclerAdapter.CompanyListHolder> {

    private ArrayList<UserList> companyListArrayList;
    private LayoutInflater inflater;

    public CompanyListRecyclerAdapter(ArrayList<UserList> studentListData, Context context) {

        companyListArrayList = studentListData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CompanyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_card_items, parent, false);

        return new CompanyListHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyListHolder holder, int position) {

        UserList userList = companyListArrayList.get(position);

        Glide.with(inflater.getContext()).load(userList.getUserImageURL()).asBitmap()
                .error(R.drawable.default_company_image).centerCrop().into(holder.companyImage);

        holder.companyName.setText(userList.getUserName());
        holder.companyWebPage.setText(userList.getUserID());
        holder.companyEmail.setText(userList.getUserEmail());

    }

    @Override
    public int getItemCount() {

        return companyListArrayList.size();
    }

    class CompanyListHolder extends RecyclerView.ViewHolder{

        private ImageView companyImage;
        private TextView companyName;
        private TextView companyWebPage;
        private TextView companyEmail;

        public CompanyListHolder(View itemView) {
            super(itemView);

            companyImage = (ImageView) itemView.findViewById(R.id.user_image);
            companyName = (TextView) itemView.findViewById(R.id.user_list_name);
            companyWebPage = (TextView) itemView.findViewById(R.id.user_list_id);
            companyEmail = (TextView) itemView.findViewById(R.id.user_list_email);
        }
    }
}
