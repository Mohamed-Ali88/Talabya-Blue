package com.talabeya.talabyablue.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.Domain.invoicesDomain;
import com.talabeya.talabyablue.R;

public class ShowInvoicesData extends AppCompatActivity {

    invoicesDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invoices_data);
    }

    public void getBundle() {
        try {
            object = (invoicesDomain) getIntent().getSerializableExtra("object");

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}