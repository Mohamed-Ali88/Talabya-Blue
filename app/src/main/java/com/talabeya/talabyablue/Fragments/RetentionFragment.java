package com.talabeya.talabyablue.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabeya.talabyablue.Adapter.RetentionAdapter;
import com.talabeya.talabyablue.Adapter.UsersAdapter;
import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetentionFragment extends Fragment {

    RecyclerView RetentionRV;
    ArrayList<UsersDomain> userArrayList = new ArrayList<>();
    SharedPreferences userPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retention, container, false);
        getViews(view);
        getData();
        return view;
    }

    private void getData() {
        int id = userPref.getInt("id", 0);
        StringRequest request = new StringRequest(Request.Method.GET, "___", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String value1 = jsonObj.getString("----");
                    String value2 = jsonObj.getString("----");
                    String value3 = jsonObj.getString("----");
                    String value4 = jsonObj.getString("----");
                    userArrayList.add(new UsersDomain("0","مدينة مبارك",
                            "01015719581", "المنصورة مدينة مبارك ش المدينة المنورة", "5", "17/5/2022",
                            "12/5/2023", "11/10/2023", "11/5/2022", "2", "7", "0", "0", "5", "0", "0", "0", "0.0ج.م",
                            "محمد احمد علي", "555641561", "2000", "1000", "5", "2", "123.232123", "95651.316584"));
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                RetentionRV.setLayoutManager(linearLayoutManager);
                UsersAdapter comAdapter = new UsersAdapter(userArrayList);
                RetentionRV.setAdapter(comAdapter);
                RetentionRV.setHasFixedSize(true);
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
    }

    private void getViews(View view) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        RetentionRV = view.findViewById(R.id.retention_rv);
    }
}