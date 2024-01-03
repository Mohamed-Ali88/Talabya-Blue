package com.talabeya.talabyablue.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.talabeya.talabyablue.Activities.TelesalesActivities.canceled_invoices;
import com.talabeya.talabyablue.Activities.TelesalesActivities.confirmed_invoices;
import com.talabeya.talabyablue.Activities.TelesalesActivities.deferred_invoices;
import com.talabeya.talabyablue.Activities.TelesalesActivities.pending;
import com.talabeya.talabyablue.Adapter.TabsFragmentAdapter;
import com.talabeya.talabyablue.Adapter.faceDataAdapter;
import com.talabeya.talabyablue.Domain.faceDataDomain;
import com.talabeya.talabyablue.Helper.Constant;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeadFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabsFragmentAdapter adapter;
    private TextView name, code;
    private Button b1, b2, b3, b4;
    private RecyclerView faceData;
    ArrayList<faceDataDomain> faceDataDomains = new ArrayList<>();
    SharedPreferences userPref;
    ScrollView sv;
    LinearLayout customerServiceLn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        getIds(view);
        checkUserType();
        setFragmentTab();
        onClicked();
        return view;
    }

    private void onClicked() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), pending.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), confirmed_invoices.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), deferred_invoices.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), canceled_invoices.class));
            }
        });
    }

    //1 client
    //2 Representative
    //3 telesales
    //4 moderator
    //5 customer serves
    private void checkUserType() {
        int userType = userPref.getInt("role", 0);
        if (userType == 1) {
            //client
            getDate("1");
        } else if (userType == 2) {
            //مندوب
            getDate("2");

        } else if (userType == 3) {
            //telesales
            getDate("2");
        } else if (userType == 4) {
            //moderators
            getDate("4");

        } else if (userType == 5) {
            //customer service
            changeDesign();
        }
        Toast.makeText(getContext(), userType + "", Toast.LENGTH_SHORT).show();
    }

    private void changeDesign() {
        sv.setVisibility(View.GONE);
        customerServiceLn.setVisibility(View.VISIBLE);
    }

    private void getDate(String role) {
        int id = userPref.getInt("id", 0);
        String type = "";
        String url = "";
        if (role.equals("2")) {
            type = "representative";
            url = Constant.URL + "/" + type + "/showRepresentative" + "/" + id;
        } else if (role.equals("3")) {
            type = "telesales";
            url = Constant.URL + "/" + type + "/showTelesales" + "/" + id;
        } else if (role.equals("4")) {
            type = "moderator";
            url = Constant.URL + "/" + type + "/showModerator" + "/" + id;
        } else if (role.equals("5")) {
            type = "customerService";
            url = Constant.URL + "/" + type + "/showCustomerService" + "/" + id;
        }
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObj = new JSONObject(response);
                String value1 = jsonObj.getString("name");
                String value2 = jsonObj.getString("phonenumber");
                name.setText(value1);
                code.setText(value2);

                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj1 = array.getJSONObject(i);
                    String value3 = jsonObj1.getString("----");
                    String value4 = jsonObj1.getString("----");
                    faceDataDomains.add(new faceDataDomain(1,value3, value4,0));
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                faceData.setLayoutManager(layoutManager);
                faceDataAdapter faceDataAdapter = new faceDataAdapter(faceDataDomains);
                faceData.setAdapter(faceDataAdapter);
                faceData.setHasFixedSize(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void setFragmentTab() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        adapter = new TabsFragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void getIds(View view) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        tabLayout = view.findViewById(R.id.tabLayout);
        customerServiceLn = view.findViewById(R.id.customerServiceLn);
        sv = view.findViewById(R.id.mainScrollView);
        faceData = view.findViewById(R.id.faceData);
        viewPager2 = view.findViewById(R.id.viewPage2);
        tabLayout.addTab(tabLayout.newTab().setText("اليوم"));
        tabLayout.addTab(tabLayout.newTab().setText("الشهر"));
        name = view.findViewById(R.id.name);
        code = view.findViewById(R.id.code);
        b1 = view.findViewById(R.id.pending);
        b2 = view.findViewById(R.id.confirmed_invoices);
        b3 = view.findViewById(R.id.deferred_invoices);
        b4 = view.findViewById(R.id.canceled_invoices);

    }
}