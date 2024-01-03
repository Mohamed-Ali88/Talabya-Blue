package com.talabeya.talabyablue.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.talabeya.talabyablue.Fragments.AllUsersFragment;
import com.talabeya.talabyablue.Fragments.LeadFragment;
import com.talabeya.talabyablue.Fragments.MapsFragment;
import com.talabeya.talabyablue.Fragments.MonthUsersFragment;
import com.talabeya.talabyablue.Fragments.RetentionFragment;
import com.talabeya.talabyablue.Fragments.UserFragment;
import com.talabeya.talabyablue.Helper.Constant;
import com.talabeya.talabyablue.R;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNav;
    TextView pageTitle;
    SharedPreferences userPref;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIds();
        bottomNavigationStuff();
        onClick();
        setSharedPreferences();
    }

    private void onClick() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog();
            }
        });
    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        pageTitle = findViewById(R.id.page_title_product);
        bottomNav = findViewById(R.id.bottomNav);
        logout = findViewById(R.id.logOut);
    }

    private void bottomNavigationStuff() {
        bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.bar));
        bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.retention));
        bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.chart));
        bottomNav.add(new MeowBottomNavigation.Model(5, R.drawable.up_down));
        bottomNav.add(new MeowBottomNavigation.Model(4, R.drawable.users));
        bottomNav.add(new MeowBottomNavigation.Model(6, R.drawable.address));
//        bottomNav.add(new MeowBottomNavigation.Model(7, R.drawable.logout));


        bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                if (item.getId() == 1) {
                    fragment = new LeadFragment();
                    setPageTitle("لوحة القيادة");
                    barColor(R.color.black);
                } else if (item.getId() == 2) {
                    fragment = new RetentionFragment();
                    setPageTitle("الرتنشن");
                    barColor(R.color.black);
                } else if (item.getId() == 3) {
                    fragment = new MonthUsersFragment();
                    barColor(R.color.black);
                    setPageTitle("عملاء الشهر");
                } else if (item.getId() == 4) {
                    fragment = new UserFragment();
                    barColor(R.color.black);
                    setPageTitle("المستخدمين");
                } else if (item.getId() == 5) {
                    fragment = new AllUsersFragment();
                    barColor(R.color.black);
                    setPageTitle("جميع العملاء");
                } else if (item.getId() == 6) {
                    fragment = new MapsFragment();
                    barColor(R.color.black);
                    setPageTitle("الخرائط");
                }
                loadFragment(fragment);
            }
        });

        bottomNav.show(1, true);

        bottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });

    }

    private void logoutDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you wish to logout?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LogOut();
                    }
                });
        alert.show();
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(MainActivity.this)
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

    private void LogOut() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.logOut, response -> {
            startActivity(new Intent(MainActivity.this, logIn.class));
            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = userPref.edit();
            editor.putBoolean("isLogged", false);
            editor.apply();
            Toast("تم تسجيل الخروج");
            finish();
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment, null)
                .commit();
    }

    private void barColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

    private void setPageTitle(String title) {
        pageTitle.setText(title);
    }

    private void setSharedPreferences() {
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("ShowRepresentative", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putInt("rep",100);
        editor.apply();
    }

}