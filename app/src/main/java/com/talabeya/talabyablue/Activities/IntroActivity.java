package com.talabeya.talabyablue.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.talabeya.talabyablue.R;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        barColor(R.color.colorAccent);
    }

    public Boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                Boolean isLoggedIn = userPref.getBoolean("isLogged", false);
                if (isLoggedIn) {
                    if (isNetworkAvailable()) {
                        Intent myIntent = new Intent(IntroActivity.this, MainActivity.class);
                        startActivity(myIntent);
                    } else {
                        startActivity(new Intent(IntroActivity.this, NoInterNet.class));
                    }
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    startActivity(new Intent(IntroActivity.this,logIn.class));
                    finish();
            }
            }
        };
        handler.postDelayed(r, 2000);
    }

    private void barColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }
}
