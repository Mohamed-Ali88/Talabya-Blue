package com.talabeya.talabyablue.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabeya.talabyablue.Adapter.UsersAdapter;
import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    RecyclerView all_users_rv2;
    ArrayList<UsersDomain> userArrayList = new ArrayList<>();
    SharedPreferences userPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        getIds(view);
        setViews();
        return view;
    }


    private void setViews() {
        int id = userPref.getInt("id", 0);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "------", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray("__________");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String v1 = jsonObject1.getString("---------");
                            String v2 = jsonObject1.getString("---------");
                            String v3 = jsonObject1.getString("---------");
                            String v4 = jsonObject1.getString("---------");
                            String v5 = jsonObject1.getString("---------");
                            String v6 = jsonObject1.getString("---------");
                            String v7 = jsonObject1.getString("---------");
                            String v8 = jsonObject1.getString("---------");
                            String v9 = jsonObject1.getString("---------");
                            String v10 = jsonObject1.getString("---------");
                            String v11 = jsonObject1.getString("---------");
                            String v12 = jsonObject1.getString("---------");
                            String v13 = jsonObject1.getString("---------");
                            String v14 = jsonObject1.getString("---------");
                            String v15 = jsonObject1.getString("---------");
                            String v16 = jsonObject1.getString("---------");
                            String v17 = jsonObject1.getString("---------");
                            String v18 = jsonObject1.getString("---------");
                            String v19 = jsonObject1.getString("---------");
                            String v20 = jsonObject1.getString("---------");
                            String v21 = jsonObject1.getString("---------");
                            String v22 = jsonObject1.getString("---------");
                            String v23 = jsonObject1.getString("---------");
                            String v24 = jsonObject1.getString("---------");
                            String v25 = jsonObject1.getString("---------");
                            userArrayList.add(new UsersDomain("0",v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11,
                                    v12, v13, v14, v15, v16, v17, v18, v19, v20, v21, v22, v23, v24, v25));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        all_users_rv2.setLayoutManager(linearLayoutManager);
                        UsersAdapter comAdapter = new UsersAdapter(userArrayList);
                        all_users_rv2.setAdapter(comAdapter);
                        all_users_rv2.setHasFixedSize(true);

                    } else {
                        Toast.makeText(getActivity(), "no daily date", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "MonthUsers : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void getIds(View view) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        all_users_rv2 = view.findViewById(R.id.all_users_rv2);
    }
}