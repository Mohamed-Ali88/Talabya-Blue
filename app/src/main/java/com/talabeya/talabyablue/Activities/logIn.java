package com.talabeya.talabyablue.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabeya.talabyablue.Helper.Constant;
import com.talabeya.talabyablue.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class logIn extends AppCompatActivity {
    EditText numberEdText, passwordEdText;
    Button setNumberBtn;
    ImageView back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getIds();
        onWriting();
        onClick();
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberEdText.getText().toString();
                String password = passwordEdText.getText().toString();

                if (number.length() != 11) {
                    numberEdText.setError("رقم الهاتف خطاء");
                    return;
                }
                if (number.isEmpty()) {
                    numberEdText.setError("رقم الهاتف خطاء");
                    return;
                }
                if (password.isEmpty()) {
                    passwordEdText.setError("ادخل الباسورد");
                    return;
                }
                login(number,password);


            }

        });
    }

    private void login(String phone, String password) {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.login, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONObject jsonUser = object.getJSONObject("user");
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userPref.edit();
                editor.putString("token", object.getString("token"));
                editor.putBoolean("isLogged", true);
                //userData
                editor.putInt("id", jsonUser.getInt("id"));
                editor.putString("name", jsonUser.getString("name"));
                editor.putString("phoneNumber", jsonUser.getString("phonenumber"));
                editor.putInt("role", jsonUser.getInt("role"));
                editor.putString("address", jsonUser.getString("address"));
                editor.putString("nationalId", jsonUser.getString("nationalid"));
                editor.apply();

                Toast("تم تسجيل الدخول بنجاح");
                startActivity(new Intent(logIn.this, IntroActivity.class));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast("انت غير مسجل عندنا");
            progressBar.setVisibility(View.GONE);
            error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("phonenumber", phone);
                map.put("password", password);
                return map;
            }
        };
        progressBar.setVisibility(View.GONE);
        Volley.newRequestQueue(this).add(request);
    }

    private void onWriting() {
        numberEdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numberEdText.getText().length() != 11) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.white));
                    ViewCompat.setBackgroundTintList(numberEdText, colorStateList);

                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(numberEdText, colorStateList);


                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getIds() {
        numberEdText = findViewById(R.id.set_new_number);
        progressBar = findViewById(R.id.Pb);
        passwordEdText = findViewById(R.id.set_password);
        setNumberBtn = findViewById(R.id.start_signUp_btn);
        back = findViewById(R.id.back);
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(logIn.this)
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

}