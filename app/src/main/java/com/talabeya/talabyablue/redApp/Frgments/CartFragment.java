package com.talabeya.talabyablue.redApp.Frgments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.talabeya.talabyablue.redApp.Adapters.cartProductAdapter;
import com.talabeya.talabyablue.Helper.ManagementCart;
import com.talabeya.talabyablue.Interface.ChangeNumberItemsListener;
import com.talabeya.talabyablue.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class CartFragment extends Fragment {
    ConstraintLayout codeConst;
    Animation fromBottom;
    ImageView noItemsCart;
    RecyclerView orderedProducts;
    TextView totalPrice;
    LinearLayout l1, l2;
    Button makeOrder;
    private ManagementCart managementCart;
    View v1;
    ScrollView scrollerViewCart;
    RadioButton dateOne, dateTwo, dateThere;
    double itemTotal = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);
        getViews(rootView);
        setAnimation();
        getFromCart();
        calculateCart();
        Clicked();
        return rootView;
    }

    public void getViews(ViewGroup view) {
        codeConst = view.findViewById(R.id.cart_cont);
        scrollerViewCart = view.findViewById(R.id.scrollerViewCart);
        l1 = view.findViewById(R.id.linearLayout1);
        l2 = view.findViewById(R.id.linearLayout2);
        v1 = view.findViewById(R.id.v1);
        noItemsCart = view.findViewById(R.id.no_items_cart);
        orderedProducts = view.findViewById(R.id.ordered_products);
        totalPrice = view.findViewById(R.id.total_price);
        dateOne = view.findViewById(R.id.date_1);
        dateTwo = view.findViewById(R.id.date_2);
        dateThere = view.findViewById(R.id.date_3);
        makeOrder = view.findViewById(R.id.makeOrder);
        managementCart = new ManagementCart(getActivity());
    }

    private void setAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.frombottom);
        codeConst.startAnimation(fromBottom);
    }

    private void getFromCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        orderedProducts.setLayoutManager(linearLayoutManager);
        RecyclerView.Adapter adapter = new cartProductAdapter(managementCart.getListCart(), getContext(), new ChangeNumberItemsListener() {
            @Override
            public void Changed() {
                calculateCart();
            }
        });
        orderedProducts.setAdapter(adapter);

        if (managementCart.getListCart().isEmpty()) {
            noItemsCart.setVisibility(View.VISIBLE);
            scrollerViewCart.setVisibility(View.GONE);
        } else {
            noItemsCart.setVisibility(View.GONE);
            scrollerViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        itemTotal = Math.round((managementCart.getTotalFee() * 100.0) / 100.0);
        totalPrice.setText(itemTotal + " ج.م");
    }

    private void Clicked() {
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemTotal == 0.0) {
                    noItemsCart.setVisibility(View.VISIBLE);
                    scrollerViewCart.setVisibility(View.GONE);
                    Toast("لا يتوفر اي منتجات في عربة مشترياتك");
                } else {
                    noItemsCart.setVisibility(View.GONE);
                    scrollerViewCart.setVisibility(View.VISIBLE);
                    makeTheOrder();
                }
            }
        });
    }

    private void makeTheOrder() {
        //send the data
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(getContext())
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }


}