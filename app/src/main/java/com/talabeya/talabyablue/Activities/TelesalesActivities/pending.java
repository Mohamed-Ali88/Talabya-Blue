package com.talabeya.talabyablue.Activities.TelesalesActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.talabeya.talabyablue.Adapter.UsersAdapter;
import com.talabeya.talabyablue.Adapter.invoicesAdapter;
import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.Domain.invoicesDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class pending extends AppCompatActivity {
    RecyclerView pending_rv;
    ArrayList<invoicesDomain> invoicesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        getViews();
        getData();
    }

    private void getData() {
        invoicesArrayList.add(new invoicesDomain("TSD#@$D","ولله معرف","12/5/2023","30/12/2023","Abo Ali","PEPE",250.0,1200.0));
        invoicesArrayList.add(new invoicesDomain("TSD#@$D","ولله معرف","12/5/2023","30/12/2023","Abo Ali","PEPE",250.0,1200.0));
        invoicesArrayList.add(new invoicesDomain("TSD#@$D","ولله معرف","12/5/2023","30/12/2023","Abo Ali","PEPE",250.0,1200.0));
        invoicesArrayList.add(new invoicesDomain("TSD#@$D","ولله معرف","12/5/2023","30/12/2023","Abo Ali","PEPE",250.0,1200.0));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pending_rv.setLayoutManager(linearLayoutManager);
        invoicesAdapter comAdapter = new invoicesAdapter(invoicesArrayList);
        pending_rv.setAdapter(comAdapter);
        pending_rv.setHasFixedSize(true);

    }

    private void getViews() {
        pending_rv = findViewById(R.id.pending_rv);
    }

}