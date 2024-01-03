package com.talabeya.talabyablue.redApp.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.talabeya.talabyablue.Domain.Product;
import com.talabeya.talabyablue.Helper.ManagementCart;
import com.talabeya.talabyablue.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class showActivityData extends AppCompatActivity {
    private TextView addToCartBtn, numberOrderTxt, titleTxt, feeTxt, descriptionTxt, totalPriceTxt, starTxt, caloriesTxt, timeTxt;
    private TextView plusBtn, minusBtn;
    private ImageView feeFood;
    private Product object;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        initView();
        getBundle();
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        plusBtn = findViewById(R.id.plusCartBtn);
        minusBtn = findViewById(R.id.minusCartBtn);
        feeFood = findViewById(R.id.foodPic);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        managementCart = new ManagementCart(this);
    }

    private void getBundle() {
        object = (Product) getIntent().getSerializableExtra("object");
        Glide.with(this).load(object.getPic()).into(feeFood);
        titleTxt.setText(object.getTitle());
        feeTxt.setText(object.getFee() + " ج.م");
        numberOrderTxt.setText(String.valueOf(numberOrder));
        totalPriceTxt.setText(Math.round(numberOrder * object.getFee()) + " ج.م");
        descriptionTxt.setText("الكرتونة = " + object.getAmount() + " علبة");
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText(Math.round(numberOrder * object.getFee()) + " ج.م");

            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText(Math.round(numberOrder * object.getFee()) + " ج.م");


            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
                Toast("تم اضافة المنتج الي عربة مشترياتك بنجاح");
            }
        });
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(showActivityData.this)
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }


}