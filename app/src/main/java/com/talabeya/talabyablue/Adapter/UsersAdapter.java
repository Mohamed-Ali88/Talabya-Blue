package com.talabeya.talabyablue.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.talabeya.talabyablue.Activities.ShowUserData;
import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private ArrayList<UsersDomain> UsersDomain;

    public UsersAdapter(ArrayList<UsersDomain> UsersDomain) {
        this.UsersDomain = UsersDomain;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_users, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        UsersDomain UserDate = UsersDomain.get(position);
        holder.userTitle.setText(UserDate.getV1());
        holder.userNumber.setText(UserDate.getV2());
        holder.userCity.setText(UserDate.getV3());
        if (UserDate.getV4().equals("0")) {
            holder.lyInfo1.setVisibility(View.GONE);
        } else {
            holder.lyInfo1.setVisibility(View.VISIBLE);
        }
        if (UserDate.getV5().equals("0")) {
            holder.lyInfo2.setVisibility(View.GONE);
        } else {
            holder.lyInfo2.setVisibility(View.VISIBLE);
        }
        if (UserDate.getV6().equals("0")) {
            holder.lyInfo3.setVisibility(View.GONE);
        } else {
            holder.lyInfo3.setVisibility(View.VISIBLE);
        }
        if (UserDate.getV7().equals("0")) {
            holder.lyInfo6.setVisibility(View.GONE);
        } else {
            holder.lyInfo6.setVisibility(View.VISIBLE);
        }
        if (UserDate.getV8().equals("0")) {
            holder.lyInfo4.setVisibility(View.GONE);
        } else {
            holder.lyInfo4.setVisibility(View.VISIBLE);
        }
        if (UserDate.getV9().equals("0")) {
            holder.lyInfo5.setVisibility(View.GONE);
        } else {
            holder.lyInfo5.setVisibility(View.VISIBLE);
        }
        holder.info1.setText(UserDate.getV4());
        holder.info2.setText(UserDate.getV5());
        holder.info3.setText(UserDate.getV6());
        holder.info4.setText(UserDate.getV8());
        holder.info5.setText(UserDate.getV9());
        holder.info6.setText(UserDate.getV7());
        holder.consUser.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), ShowUserData.class);
            intent.putExtra("object", UsersDomain.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return UsersDomain.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userTitle, userNumber, userCity, info1, info2, info6, info3, info4, info5;
        LinearLayout  lyInfo1, lyInfo2, lyInfo3, lyInfo4, lyInfo5, lyInfo6;
        ConstraintLayout consUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consUser = itemView.findViewById(R.id.users_ly);
            userTitle = itemView.findViewById(R.id.users_title);
            userNumber = itemView.findViewById(R.id.users_number);
            userCity = itemView.findViewById(R.id.users_city);
            info1 = itemView.findViewById(R.id.Info1);
            info2 = itemView.findViewById(R.id.Info2);
            info3 = itemView.findViewById(R.id.Info3);
            info4 = itemView.findViewById(R.id.Info4);
            info5 = itemView.findViewById(R.id.Info5);
            info6 = itemView.findViewById(R.id.Info6);
            lyInfo1 = itemView.findViewById(R.id.lyInfo1);
            lyInfo2 = itemView.findViewById(R.id.lyInfo2);
            lyInfo3 = itemView.findViewById(R.id.lyInfo3);
            lyInfo4 = itemView.findViewById(R.id.lyInfo4);
            lyInfo5 = itemView.findViewById(R.id.lyInfo5);
            lyInfo6 = itemView.findViewById(R.id.lyInfo6);
        }
    }

}
