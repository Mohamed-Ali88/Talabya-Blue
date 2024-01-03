package com.talabeya.talabyablue.redApp.Frgments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabeya.talabyablue.redApp.Adapters.OrdersAdapter;
import com.talabeya.talabyablue.Domain.ordersDomain;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    RecyclerView orderedStates;
    TextView totalOrderedPrice, totalOrderedNumber;
    LinearLayout ll1;
    RelativeLayout orderConst;
    Animation fromBottom;
    ArrayList<ordersDomain> ordersDomainArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_orders, container, false);
        getIds(rootView);
        setAnimation();
//        statAnimation();
//        getTopDate();
//        getOrderedStates();
        trydesign();
        return rootView;
    }

    private void setAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.frombottom);
        orderConst.startAnimation(fromBottom);
    }

    private void getTopDate() {
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
                            String total_ordered_number = jsonObject1.getString("---------");
                            String total_ordered_price = jsonObject1.getString("----------");
                            totalOrderedPrice.setText(total_ordered_price);
                            totalOrderedNumber.setText(total_ordered_number);

                        }
                    } else {
                        Toast.makeText(getContext(), "no top date", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "top date : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getOrderedStates() {
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
                            String id = jsonObject1.getString("---------");
                            String state = jsonObject1.getString("----------");
                            String price = jsonObject1.getString("----------");
                            String date = jsonObject1.getString("----------");
                            ordersDomainArrayList.add(new ordersDomain(id, state,price,date));
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                        orderedStates.setLayoutManager(layoutManager);
                        OrdersAdapter catAdapter = new OrdersAdapter(ordersDomainArrayList);
                        orderedStates.setAdapter(catAdapter);
                        orderedStates.setHasFixedSize(true);
                    } else {
                        orderedStates.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "no orders state date", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Orders : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void trydesign(){
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل","5000 ج.م","12/5/2012"));
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل","5000 ج.م","12/5/2012"));
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل","5000 ج.م","12/5/2012"));
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل","5000 ج.م","12/5/2012"));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        orderedStates.setLayoutManager(layoutManager);
        OrdersAdapter catAdapter = new OrdersAdapter(ordersDomainArrayList);
        orderedStates.setAdapter(catAdapter);
        orderedStates.setHasFixedSize(true);

    }

    private void getIds(ViewGroup rootView) {
        orderedStates = rootView.findViewById(R.id.ordered_state);
        orderConst = rootView.findViewById(R.id.orderConst);
        ll1 = rootView.findViewById(R.id.ll1);
        totalOrderedPrice = rootView.findViewById(R.id.total_ordered_price);
        totalOrderedNumber = rootView.findViewById(R.id.total_ordered_number);
    }


}