package com.talabeya.talabyablue.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.talabeya.talabyablue.Activities.ShowRepresentative;
import com.talabeya.talabyablue.Activities.ShowUserData;
import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.Domain.faceDataDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class faceDataAdapter extends RecyclerView.Adapter<faceDataAdapter.ViewHolder> {

    private ArrayList<faceDataDomain> UsersDomain;

    public faceDataAdapter(ArrayList<faceDataDomain> UsersDomain) {
        this.UsersDomain = UsersDomain;
    }

    @NonNull
    @Override
    public faceDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_facedata, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull faceDataAdapter.ViewHolder holder, int position) {
        faceDataDomain UserDate = UsersDomain.get(position);
        holder.userName.setText(UserDate.getName());
        holder.nextData.setText(UserDate.getNextData());
        if(UserDate.getShowRepresentativeActivity() == 0){
            holder.usersLy.setOnClickListener(view -> {
                Intent intent = new Intent(holder.itemView.getContext(), ShowRepresentative.class);
                intent.putExtra("object", UsersDomain.get(position));
                holder.itemView.getContext().startActivity(intent);
            });

        }

    }

    @Override
    public int getItemCount() {
        return UsersDomain.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, nextData;
        ConstraintLayout usersLy;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usersLy = itemView.findViewById(R.id.users_ly);
            nextData = itemView.findViewById(R.id.user_next);
            userName = itemView.findViewById(R.id.user_name);
        }
    }

}
