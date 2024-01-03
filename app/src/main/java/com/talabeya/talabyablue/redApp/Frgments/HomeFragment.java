package com.talabeya.talabyablue.redApp.Frgments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.talabeya.talabyablue.Domain.Product;
import com.talabeya.talabyablue.Domain.comCatDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    TextView catTitle, comTitle, proTitle;
    ImageSlider dailySale;
    ConstraintLayout homeConst;
    Animation fromBottom;
    RecyclerView companies, categories, promotions;
    ArrayList<comCatDomain> catArrayList = new ArrayList<>();
    ArrayList<comCatDomain> comArrayList = new ArrayList<>();
    ArrayList<Product> horizontalProductArrayList = new ArrayList<>();
    ArrayList<SlideModel> slideModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        getIds(rootView);
        setStartAnimation(rootView);
        // setDailySale();
        // setCompaniesView();
       // categoriesView();
        // promotions();
        // setTestDesign();
        return rootView;


    }



    private void setStartAnimation(ViewGroup rootView) {
        homeConst = rootView.findViewById(R.id.homeConst);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.frombottom);
        homeConst.startAnimation(fromBottom);
    }

    private void getIds(ViewGroup rootView) {
        dailySale = rootView.findViewById(R.id.daily_sales);
        catTitle = rootView.findViewById(R.id.cat_title);
        proTitle = rootView.findViewById(R.id.pro_title);
        comTitle = rootView.findViewById(R.id.com_title);
        promotions = rootView.findViewById(R.id.promotion_main);
        companies = rootView.findViewById(R.id.companies);
        categories = rootView.findViewById(R.id.categories_view);
    }


}