package com.talabeya.talabyablue.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

public class ShowRepresentative extends AppCompatActivity {
    faceDataDomain object;
    SharedPreferences userPref;
    private TextView name, code;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private RecyclerView faceData;
    private TabsFragmentAdapter adapter;
    ArrayList<faceDataDomain> faceDataDomains = new ArrayList<>();
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_representative);
        setSharedPreferences(0);
        getIds();
        getBundle();
        setFragmentTab();
        getDate();
    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name = findViewById(R.id.name);
        tabLayout = findViewById(R.id.tabLayout);
        code = findViewById(R.id.code);
        faceData = findViewById(R.id.faceData);
        viewPager2 = findViewById(R.id.viewPage2);
        tabLayout.addTab(tabLayout.newTab().setText("اليوم"));
        tabLayout.addTab(tabLayout.newTab().setText("الشهر"));

    }

    public void getBundle() {
        try {
            object = (faceDataDomain) getIntent().getSerializableExtra("object");
            object.getId();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setFragmentTab() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
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

    private void getDate() {
        String url = Constant.URL + "/representative/showRepresentative" + "/" + id;
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String value1 = jsonObj.getString("----");
                    String value2 = jsonObj.getString("----");
                    String value3 = jsonObj.getString("----");
                    String value4 = jsonObj.getString("----");
                    name.setText(value1);
                    code.setText(value2);
                    faceDataDomains.add(new faceDataDomain(1, value3, value4, 0));
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void setSharedPreferences(int i) {
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("ShowRepresentative", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putInt("rep",i);
        editor.putInt("repID",id);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        setSharedPreferences(100);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        setSharedPreferences(100);
        super.onStop();
    }

    @Override
    protected void onPause() {
        setSharedPreferences(100);
        super.onPause();
    }
}