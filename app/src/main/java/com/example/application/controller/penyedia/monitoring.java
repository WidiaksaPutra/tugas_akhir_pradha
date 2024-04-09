package com.example.application.controller.penyedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.model.API.APISQL;
import com.example.application.model.manager.Retroserver;
import com.example.application.R;
import com.example.application.model.adapter.adapterMonitoring;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class monitoring extends AppCompatActivity{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<SelectModel> mItems = new ArrayList<>();
    ProgressDialog pd;
    TextView keys1,keys2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring);
        keys1 = (TextView) findViewById(R.id.key1);
        keys2 = (TextView) findViewById(R.id.key2);
        Intent data = getIntent();
        keys1.setText(data.getStringExtra("keypradha1"));
        keys2.setText(data.getStringExtra("keypradha2"));

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.user);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading ...");
        pd.setCancelable(false);
        pd.show();
        String key1 = keys1.getText().toString();
        String key2 = keys2.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> getdata = api.monitoringAdmin(key1,key2);
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();

               mAdapter = new adapterMonitoring(monitoring.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                pd.hide();
                Log.d("RETRO", "FAILED : respon gagal ");
            }
        });
    }
    public void backmonitor (View v){
        Intent back = new Intent(monitoring.this, penyedia2.class);
        startActivity(back);
    }
}