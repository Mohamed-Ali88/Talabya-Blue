package com.talabeya.talabyablue.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.talabeya.talabyablue.Helper.Constant;
import com.talabeya.talabyablue.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RetentionMonth extends Fragment {
    ArcProgress ap1, ap2, ap3, ap4, ap5, ap6, ap7, ap8, ap9, ap10;
    SharedPreferences userPref,representativePref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retention_month, container, false);
        getIds(view);
        checkUserType();
        return view;
    }

    //1 client
    //2 Representative
    //3 telesales
    //4 moderator
    //5 customer serves
    private void checkUserType() {
        int userType = userPref.getInt("role", 0);
        int representative = representativePref.getInt("rep", 10);
        Toast.makeText(getContext(), representative+"", Toast.LENGTH_SHORT).show();
        if (userType == 1) {
            //client
            getDate("1");
        } else if (userType == 2) {
            //مندوب
            getDate("2");

        } else if (userType == 3) {
            //telesales
            if (representative == 0) {
                Representative();
            } else {
                getDate("3");
            }

        } else if (userType == 4) {
            //moderators
            if (representative == 0) {
                Representative();
            } else {
                getDate("4");
            }

        } else if (userType == 5) {
            //customer service
            getDate("5");
        }
    }

    private void Representative() {
        int representativeID = userPref.getInt("repID", 0);
        String url = Constant.URL + "/representative/showRepresentative/" + representativeID;

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObj = new JSONObject(response);
                int value1 = jsonObj.getInt("----");
                int value2 = jsonObj.getInt("----");
                int value3 = jsonObj.getInt("----");
                int value4 = jsonObj.getInt("----");
                int value5 = jsonObj.getInt("----");
                int value6 = jsonObj.getInt("----");
                setProgress(value1, value2, value3, value4, value5, value6);
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

    private void getDate(String role) {
        int id = userPref.getInt("id", 0);
        String type = "";
        String url = "";
        if (role.equals("2")) {
            type = "representative";
            url = Constant.URL + "/" + type + "showRepresentative" + "/" + id;
        } else if (role.equals("3")) {
            type = "telesales";
            url = Constant.URL + "/" + type + "showTelesales" + "/" + id;
        } else if (role.equals("4")) {
            type = "moderator";
            url = Constant.URL + "/" + type + "showModerator" + "/" + id;
        } else if (role.equals("5")) {
            type = "customerService";
            url = Constant.URL + "/" + type + "showCustomerService" + "/" + id;
        }
        //2 Representative
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObj = new JSONObject(response);
                int value1 = jsonObj.getInt("----");
                int value2 = jsonObj.getInt("----");
                int value3 = jsonObj.getInt("----");
                int value4 = jsonObj.getInt("----");
                int value5 = jsonObj.getInt("----");
                int value6 = jsonObj.getInt("----");
                setProgress(value1,value2,value3,value4,value5,value6);
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

    private void setProgress(int p1,int p2,int p3,int p4,int p5,int p6) {
        ap1.setProgress(p1);
        ap2.setProgress(p2);
        ap3.setProgress(p3);
        ap4.setProgress(p4);
        ap5.setProgress(p5);
        ap6.setProgress(p6);
    }

    private void getIds(View view) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        representativePref = getActivity().getApplicationContext().getSharedPreferences("ShowRepresentative", getContext().MODE_PRIVATE);
        ap1 = view.findViewById(R.id.progress_1);
        ap2 = view.findViewById(R.id.progress_2);
        ap3 = view.findViewById(R.id.progress_3);
        ap4 = view.findViewById(R.id.progress_4);
        ap5 = view.findViewById(R.id.progress_5);
        ap6 = view.findViewById(R.id.progress_6);
        ap7 = view.findViewById(R.id.progress_7);
        ap8 = view.findViewById(R.id.progress_8);
        ap9 = view.findViewById(R.id.progress_9);
        ap10 = view.findViewById(R.id.progress_10);
    }

}