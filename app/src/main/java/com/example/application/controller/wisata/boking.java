package com.example.application.controller.wisata;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.GisWisata;
import com.example.application.R;
import com.example.application.model.API.APISQL;
import com.example.application.model.adapter.bokingan;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class boking extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<SelectModel> mItems = new ArrayList<>();
    SessionManager sm;
    TextView keys,latitude,langitude;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bokingwisata);
        keys = (TextView) findViewById(R.id.key);
        latitude = (TextView) findViewById(R.id.lat);
        langitude = (TextView) findViewById(R.id.lang);
        sm = new SessionManager(boking.this);
        HashMap<String, String> map = sm.getDetailLogin();
        keys.setText(map.get(sm.KEY));
        HashMap<String, String> map1 = sm.getUserLocation();
        latitude.setText(map1.get(sm.UserLatitude));
        langitude.setText(map1.get(sm.UserLongitude));
        mRecycler = (RecyclerView) findViewById(R.id.boking);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        String key1 = keys.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> getdata = api.bokingan(key1);
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {

                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();

                mAdapter = new bokingan(boking.this ,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                Log.d("RETRO", "FAILED : respon gagal ");
            }
        });
    }
    public void backpublick (View v){
        Intent back = new Intent(boking.this, wisataumum.class);
        startActivity(back);
    }

    public void RMaps (View v){
        String key1 = keys.getText().toString();
        String Lat = latitude.getText().toString();
        String Lang = langitude.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> getdata =  api.cih(key1,Lat,Lang);
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                mItems = response.body().getResult();
                int jm = mItems.size();
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                Bundle maps = new Bundle();
                maps.putInt("jumlah", jm);
                Intent map = new Intent(boking.this, GisWisata.class);
                map.putExtras(maps);
                for(int i = 0; i<jm; i++){
                    SelectModel dm = mItems.get(i);
                    map.putExtra("Nama_tempat"+i, dm.getNama_tempat());
                    map.putExtra("Latitude"+i, dm.getLatitude());
                    map.putExtra("Longitude"+i, dm.getLongitude());
                }
                startActivity(map);
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                Log.d("RETRO", "FAILED : respon gagal ");
            }
        });
    }

    public void delete (View v){
        String key1 = keys.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> getdata = api.deleteBooking(key1);
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {
                    cursor.moveToFirst();
                    Intent booking = new Intent(boking.this, boking.class);
                    startActivity(booking);
                    Toast.makeText(boking.this, "Success Delete All Booking..", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                Log.d("RETRO", "Falure : " + "Riquest Failed");
            }
        });
    }
}