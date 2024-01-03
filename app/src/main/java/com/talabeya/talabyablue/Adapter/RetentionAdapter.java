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

public class RetentionAdapter extends RecyclerView.Adapter<RetentionAdapter.ViewHolder> {

    private ArrayList<UsersDomain> UsersDomain;

    public RetentionAdapter(ArrayList<UsersDomain> UsersDomain) {
        this.UsersDomain = UsersDomain;
    }

    @NonNull
    @Override
    public RetentionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_retention, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetentionAdapter.ViewHolder holder, int position) {
        UsersDomain UserDate = UsersDomain.get(position);
        holder.userTitle.setText(UserDate.getV1());
        holder.userNumber.setText(UserDate.getV2());
        holder.userCity.setText(UserDate.getV3());
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
        TextView userTitle, userType, userNumber, userCity;
        ConstraintLayout consUser;
        ImageView trueImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consUser = itemView.findViewById(R.id.users_ly);
            userTitle = itemView.findViewById(R.id.users_title);
            userNumber = itemView.findViewById(R.id.users_number);
            userCity = itemView.findViewById(R.id.users_city);
        }
    }

}
