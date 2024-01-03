package com.talabeya.talabyablue.redApp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabeya.talabyablue.redApp.Adapters.CatMainAdapter;
import com.talabeya.talabyablue.redApp.Adapters.CatSubAdapter;
import com.talabeya.talabyablue.redApp.Adapters.productHorizontalAdapter;
import com.talabeya.talabyablue.Domain.Product;
import com.talabeya.talabyablue.Domain.comCatDomain;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class showCat extends AppCompatActivity {
    RecyclerView categories, promotions;
    comCatDomain object;
    TextView proTitle, pageTitle;
    ImageView back;
    String id;
    ArrayList<comCatDomain> catArrayList = new ArrayList<>();
    ArrayList<Product> horizontalProductArrayList = new ArrayList<>();
    String urlImage = "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/promotions.png?alt=media&token=f9035e82-7856-47a2-ba69-15bc208882c4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cat);
        getIds();
       // categoriesView();
       // promotions();
        onClicked();
        getBundle();
        startDesign();
    }
    private void startDesign() {
        horizontalProductArrayList.add(new Product("كولا", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 310.0, 0.0));
        horizontalProductArrayList.add(new Product("شوبس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593117.jpg?alt=media&token=740de0ec-f5be-4358-a0c2-d42980f7e568", 12, 310.0, 0.0));
        horizontalProductArrayList.add(new Product("المراعي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096605.jpg?alt=media&token=14d15ef7-4f0d-4d9e-92e7-8e5399980df7", 12, 310.0, 500.0));
        horizontalProductArrayList.add(new Product("عبور لاند", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096635.jpg?alt=media&token=f09c9e98-7163-4a99-9e0a-bc4e1768c731", 12, 310.0, 500.0));
        horizontalProductArrayList.add(new Product("مكس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008373.jpg?alt=media&token=79ea8538-31be-496a-ba60-5cb1c0d8b270", 12, 310.0, 500.0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(showCat.this, LinearLayoutManager.HORIZONTAL, false);
        promotions.setLayoutManager(linearLayoutManager);
        productHorizontalAdapter productHorizontalAdapter = new productHorizontalAdapter(showCat.this, horizontalProductArrayList);
        promotions.setAdapter(productHorizontalAdapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(promotions);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (productHorizontalAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(promotions, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (productHorizontalAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(promotions, new RecyclerView.State(), 0);

                }
            }
        }, 0, 3000);


        //cats
        switch (id) {
            case "1":
                catArrayList.add(new comCatDomain("اسماك و لحوم", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D9%85%D8%B9%D9%84%D8%A8%D8%A7%D8%AA%2F1701270148140.jpg?alt=media&token=0018295d-4a05-4ffb-97f2-387013b56829", ""));
                break;
            case "2":
                catArrayList.add(new comCatDomain("ستنج", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593142.jpg?alt=media&token=08f69268-284b-4d15-b1bb-f1dc467caba7", ""));
                catArrayList.add(new comCatDomain("شويبس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593117.jpg?alt=media&token=740de0ec-f5be-4358-a0c2-d42980f7e568", ""));
                catArrayList.add(new comCatDomain("ريدبول", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593108.jpg?alt=media&token=9475178a-94b6-4715-ac35-590c8ed6afec", ""));
                catArrayList.add(new comCatDomain("بيبسي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593102.jpg?alt=media&token=bd91121d-963a-40c4-8ce7-07fec108a5b2", ""));
                catArrayList.add(new comCatDomain("موسي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593093.jpg?alt=media&token=cbcd74a6-f4a9-47ab-b5b7-97779d0abcd1", ""));
                catArrayList.add(new comCatDomain("كوكاكولا", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", ""));

                break;
            case "3":
                catArrayList.add(new comCatDomain("شاي ربيع", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543914.jpg?alt=media&token=9e3fa256-7a6c-4006-87ee-6a4e237d54a4", ""));
                catArrayList.add(new comCatDomain("بوجورنو", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543907.jpg?alt=media&token=c327b315-cfa0-4a9e-aed6-4fc55434705a", ""));
                catArrayList.add(new comCatDomain("كوفي بريك", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543900.jpg?alt=media&token=2bf151a7-6b23-4575-ba51-a7f58789f776", ""));
                catArrayList.add(new comCatDomain("ايزيس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543892.jpg?alt=media&token=590aff16-eff1-4862-81e4-a77e52552a7e", ""));
                catArrayList.add(new comCatDomain("ليبتون", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543887.jpg?alt=media&token=d62f50e0-a89b-4d5d-98de-c57ae4dc32a8", ""));
                catArrayList.add(new comCatDomain("نسكافيه", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543880.jpg?alt=media&token=48ac1626-0fae-4973-af0c-62427a87769e", ""));
                catArrayList.add(new comCatDomain("ابو عوف", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543874.jpg?alt=media&token=6dfb3624-17ef-4ef9-b666-9b3630ed8deb", ""));
                catArrayList.add(new comCatDomain("العروسة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543869.jpg?alt=media&token=3ca4ec51-bdfe-45dd-9fe8-40838ac11ea4", ""));
                catArrayList.add(new comCatDomain("عبد المعبود", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/hotDirnks%2F1701269543860.jpg?alt=media&token=f46496c6-e5cd-43b4-a910-d2eef2972dba", ""));

                break;
            case "4":
                catArrayList.add(new comCatDomain("سم توب", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008422.jpg?alt=media&token=22daaecc-4599-4c99-9802-18107f7ac8b7", ""));
                catArrayList.add(new comCatDomain("راني", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008422.jpg?alt=media&token=22daaecc-4599-4c99-9802-18107f7ac8b7", ""));
                catArrayList.add(new comCatDomain("جهينة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008403.jpg?alt=media&token=2ab12943-75ad-4565-b58b-58fa13ce870d", ""));
                catArrayList.add(new comCatDomain("بيتي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008394.jpg?alt=media&token=17e531c9-aa74-4646-8c52-6b05db357990", ""));
                catArrayList.add(new comCatDomain("بيست", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008385.jpg?alt=media&token=b330aca1-dfac-4816-9149-c5f80808e9da", ""));
                catArrayList.add(new comCatDomain("مكسات", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008373.jpg?alt=media&token=79ea8538-31be-496a-ba60-5cb1c0d8b270", ""));

                break;
            case "5":
                catArrayList.add(new comCatDomain("", "", ""));

                break;
            case "6":
                catArrayList.add(new comCatDomain("دوريتوس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%AD%D9%84%D9%88%D9%8A%D8%A7%D8%AA%20%D8%A7%D8%B3%D9%86%D8%A7%D9%83%D8%B3%2F1701270051880.jpg?alt=media&token=c6c0eadd-87e5-4f7e-9dfd-12ff464d5351", ""));
                catArrayList.add(new comCatDomain("ايديتا", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%AD%D9%84%D9%88%D9%8A%D8%A7%D8%AA%20%D8%A7%D8%B3%D9%86%D8%A7%D9%83%D8%B3%2F1701270051870.jpg?alt=media&token=ba672478-6faf-427b-bf7d-3d50d20bf8af", ""));
                catArrayList.add(new comCatDomain("صن بايتس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%AD%D9%84%D9%88%D9%8A%D8%A7%D8%AA%20%D8%A7%D8%B3%D9%86%D8%A7%D9%83%D8%B3%2F1701270051862.jpg?alt=media&token=bfb652ef-22a6-4365-ac1a-4a639ce6076a", ""));
                catArrayList.add(new comCatDomain("شيبسي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%AD%D9%84%D9%88%D9%8A%D8%A7%D8%AA%20%D8%A7%D8%B3%D9%86%D8%A7%D9%83%D8%B3%2F1701270051855.jpg?alt=media&token=5db8cbfa-9ec9-435b-b9da-5d3117cf4fd4", ""));

                break;
            case "7":
                catArrayList.add(new comCatDomain("لمار", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096644.jpg?alt=media&token=c8ac267d-2bb8-4a14-9f5b-65f5130cd043", ""));
                catArrayList.add(new comCatDomain("عبورلاند", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096635.jpg?alt=media&token=f09c9e98-7163-4a99-9e0a-bc4e1768c731", ""));
                catArrayList.add(new comCatDomain("دومتي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096628.jpg?alt=media&token=90fe8df6-18ef-4cb1-8825-e68378c5ad33", ""));
                catArrayList.add(new comCatDomain("جهينة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096620.jpg?alt=media&token=5e90871f-f9cb-4301-b21e-7caea2f9fe53", ""));
                catArrayList.add(new comCatDomain("بخيرة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096612.jpg?alt=media&token=7a8a8065-591e-48b1-9bc6-3e56f6bb12f5", ""));
                catArrayList.add(new comCatDomain("المراعي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096605.jpg?alt=media&token=14d15ef7-4f0d-4d9e-92e7-8e5399980df7", ""));
                catArrayList.add(new comCatDomain("نيدو", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096595.jpg?alt=media&token=f48a4020-68a9-4305-ad1e-08d4b1798c45", ""));

                break;
            case "8":
                catArrayList.add(new comCatDomain("مرقه و توابل", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494411.jpg?alt=media&token=9b3be647-10dd-4bd3-a072-eebf1094793a", ""));
                catArrayList.add(new comCatDomain("مربي و حلاوه", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494404.jpg?alt=media&token=e4f7460d-d0b4-4887-9550-7c0f6d846c53", ""));
                catArrayList.add(new comCatDomain("كاتشب", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494398.jpg?alt=media&token=46b407f5-b98c-4758-915d-9cfe6b73023e", ""));
                catArrayList.add(new comCatDomain("عسل و طحينة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494392.jpg?alt=media&token=1a3a799f-3d38-4a01-8076-8ae517ad566a", ""));
                catArrayList.add(new comCatDomain("نودلز", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494382.jpg?alt=media&token=aa263ed3-bd7f-48f0-9398-50e50070578c", ""));
                catArrayList.add(new comCatDomain("خل و صلصة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494374.jpg?alt=media&token=266215ed-58c7-4d29-80db-865ae822a01d", ""));
                catArrayList.add(new comCatDomain("دقيق و ارز", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494368.jpg?alt=media&token=48e90435-c30c-48fe-8254-ad37a4bc7b3e", ""));
                catArrayList.add(new comCatDomain("سكر و ملح", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494362.jpg?alt=media&token=4bf621f3-6d8b-4007-aff8-00348789ec74", ""));
                catArrayList.add(new comCatDomain("مكرونة و بقوليات", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/mainThings%2F1701269494349.jpg?alt=media&token=b650dc4e-fb06-4efb-aabc-d87617e68718", ""));
                break;
            case "9":
                catArrayList.add(new comCatDomain("", "", ""));
                break;
        }


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showCat.this, 3);
        categories.setLayoutManager(layoutManager);
        CatSubAdapter catAdapter = new CatSubAdapter(catArrayList);
        categories.setAdapter(catAdapter);
        categories.setHasFixedSize(true);


    }

    private void categoriesView() {
        String URL = "---------";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray("__________");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String title = jsonObject1.getString("---------");
                            String pic = jsonObject1.getString("----------");
                            // catArrayList.add(new comCatDomain(title, pic));
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showCat.this, 3);
                        categories.setLayoutManager(layoutManager);
                        CatMainAdapter catAdapter = new CatMainAdapter(catArrayList);
                        categories.setAdapter(catAdapter);
                        categories.setHasFixedSize(true);
                    } else {
                        categories.setVisibility(View.GONE);
                        Toast.makeText(showCat.this, "no cats date", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(showCat.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(showCat.this, "Cat: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(showCat.this);
        requestQueue.add(stringRequest);

    }

    private void getBundle() {
        object = (comCatDomain) getIntent().getSerializableExtra("object");
        id = object.getId();
        pageTitle.setText(object.getTitle());
    }

    private void getIds() {
        categories = findViewById(R.id.cats);
        promotions = findViewById(R.id.promotion_cats);
        proTitle = findViewById(R.id.tvPromotion);
        back = findViewById(R.id.backCat);
        pageTitle = findViewById(R.id.page_title_cats);
    }

    private void onClicked() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void promotions() {
        String URL = "---------";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray("__________");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String title = jsonObject1.getString("---------");
                            String pic = jsonObject1.getString("----------");
                            double price = jsonObject1.getDouble("---------");
                            double discountPrice = jsonObject1.getDouble("----------");
                            int amount = jsonObject1.getInt("---------");
                            horizontalProductArrayList.add(new Product(title, pic, amount, price, discountPrice));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(showCat.this, LinearLayoutManager.HORIZONTAL, false);
                        promotions.setLayoutManager(linearLayoutManager);
                        productHorizontalAdapter productHorizontalAdapter = new productHorizontalAdapter(showCat.this,horizontalProductArrayList);
                        promotions.setAdapter(productHorizontalAdapter);
                        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
                        linearSnapHelper.attachToRecyclerView(promotions);
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (productHorizontalAdapter.getItemCount() - 1)) {
                                    linearLayoutManager.smoothScrollToPosition(promotions, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (productHorizontalAdapter.getItemCount() - 1)) {
                                    linearLayoutManager.smoothScrollToPosition(promotions, new RecyclerView.State(), 0);

                                }
                            }
                        }, 0, 3000);
                    } else {
                        promotions.setVisibility(View.GONE);
                        proTitle.setVisibility(View.GONE);
                        Toast.makeText(showCat.this, "no pro date", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(showCat.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(showCat.this, "Pro : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(showCat.this);
        requestQueue.add(stringRequest);
    }
}