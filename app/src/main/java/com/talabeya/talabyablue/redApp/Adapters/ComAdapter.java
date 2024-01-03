package com.talabeya.talabyablue.redApp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.talabeya.talabyablue.redApp.Activities.showProducts;
import com.talabeya.talabyablue.Domain.comCatDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class ComAdapter extends RecyclerView.Adapter<ComAdapter.ViewHolder> {

    private ArrayList<comCatDomain> comDomain;

    public ComAdapter(ArrayList<comCatDomain> CatDomain) {
        this.comDomain = CatDomain;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat_and_companies_2, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.comName.setText(comDomain.get(position).getTitle());
        Glide.with(holder.itemView.getContext()).load(comDomain.get(position).getPic()).into(holder.comPic);
        holder.comLy.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), showProducts.class);
            intent.putExtra("object2", comDomain.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return comDomain.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView comLy;
        TextView comName;
        ImageView comPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comLy = itemView.findViewById(R.id.com_cat_ly);
            comName = itemView.findViewById(R.id.com_cat_name);
            comPic = itemView.findViewById(R.id.com_cat_img);
        }
    }

}
