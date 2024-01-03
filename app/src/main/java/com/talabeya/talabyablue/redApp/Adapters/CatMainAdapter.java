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
import com.talabeya.talabyablue.redApp.Activities.showCat;
import com.talabeya.talabyablue.Domain.comCatDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class CatMainAdapter extends RecyclerView.Adapter<CatMainAdapter.ViewHolder> {

    private ArrayList<comCatDomain> catDomain;

    public CatMainAdapter(ArrayList<comCatDomain> CatDomain) {
        this.catDomain = CatDomain;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat_and_companies, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        comCatDomain comCatDate = catDomain.get(position);
        holder.catName.setText(comCatDate.getTitle());
        Glide.with(holder.itemView.getContext()).load(catDomain.get(position).getPic()).into(holder.catPic);
        holder.catLy.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), showCat.class);
            intent.putExtra("object", catDomain.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return catDomain.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView catLy;
        TextView catName;
        ImageView catPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catLy = itemView.findViewById(R.id.com_cat_ly);
            catName = itemView.findViewById(R.id.com_cat_name);
            catPic = itemView.findViewById(R.id.com_cat_img);
        }
    }

}
