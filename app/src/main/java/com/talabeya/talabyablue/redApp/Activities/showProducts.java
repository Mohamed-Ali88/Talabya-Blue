package com.talabeya.talabyablue.redApp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabeya.talabyablue.redApp.Adapters.productNormalAdapter;
import com.talabeya.talabyablue.Domain.Product;
import com.talabeya.talabyablue.Domain.comCatDomain;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class showProducts extends AppCompatActivity {

    RecyclerView products;
    ArrayList<Product> productArrayList = new ArrayList<>();
    TextView noItems,titlePage;
    comCatDomain object;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prodcuts);
        getIds();
        getBundle();
     //   setProducts();
        startDesign();
        onClicked();
    }

    private void startDesign() {
        productArrayList.add(new Product("كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
        productArrayList.add(new Product("كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
        productArrayList.add(new Product("كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
        productArrayList.add(new Product("كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showProducts.this, 2);
        products.setLayoutManager(layoutManager);
        productNormalAdapter productNormalAdapter = new productNormalAdapter(productArrayList);
        products.setAdapter(productNormalAdapter);
        products.setHasFixedSize(true);
    }

    private void getBundle() {
        object = (comCatDomain) getIntent().getSerializableExtra("object2");
        titlePage.setText(object.getTitle());
    }

    private void setProducts() {
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
                            productArrayList.add(new Product(title, pic, amount, price, discountPrice));
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showProducts.this, 2);
                        products.setLayoutManager(layoutManager);
                        productNormalAdapter productNormalAdapter = new productNormalAdapter(productArrayList);
                        products.setAdapter(productNormalAdapter);
                        products.setHasFixedSize(true);
                    } else {
                        products.setVisibility(View.GONE);
                        noItems.setVisibility(View.VISIBLE);
                        Toast.makeText(showProducts.this, "no product date", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(showProducts.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(showProducts.this, "Pro : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getIds() {
        products = findViewById(R.id.products);
        back = findViewById(R.id.back);
        titlePage = findViewById(R.id.title_product_page);
        noItems = findViewById(R.id.no_items);
    }

    private void onClicked(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}