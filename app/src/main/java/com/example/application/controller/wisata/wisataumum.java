package com.example.application.controller.wisata;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.GisWisata;
import com.example.application.R;
import com.example.application.controller.awaluser.login;
import com.example.application.model.API.APISQL;
import com.example.application.model.adapter.adapterdata;
import com.example.application.model.manager.Retroserver;
import com.example.application.model.manager.SessionManager;
import com.example.application.model.phpmodel.ResponsModel;
import com.example.application.model.phpmodel.SelectModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wisataumum extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<SelectModel> mItems = new ArrayList<>();
    private List<SelectModel> mItems2 = new ArrayList<>();
    TextView Longitude;
    TextView Latitude;
    String keyy;
//    ProgressDialog pd;
    SessionManager sm;
    TextView keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuwisata);
        drawerLayout = findViewById(R.id.activity_main);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        keys = (TextView) findViewById(R.id.key);

        sm = new SessionManager(wisataumum.this);
        HashMap<String, String> map = sm.getDetailLogin();
        keys.setText(map.get(sm.KEY));

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        test
        Latitude = (TextView) findViewById(R.id.userlatitude);
        Longitude = (TextView) findViewById(R.id.userlongitude);

        HashMap<String, String> map1 = sm.getUserLocation();
        Latitude.setText(map1.get(sm.UserLatitude));
        Longitude.setText(map1.get(sm.UserLongitude));
        Button budaya = (Button) findViewById(R.id.wisatabudaya);
        budaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wisatabudaya = new Intent(wisataumum.this, wisatabudaya.class);
                startActivity(wisatabudaya);
            }
        });

//        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.wisataumum);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
//        pd.setMessage("Loading ...");
//        pd.setCancelable(false);
//        pd.show();
        keyy = keys.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> Naive = api.sendNaive(keyy);
        Naive.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
//                pd.hide();
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {
//                    Toast.makeText(wisataumum.this, "Data key Masuk..", Toast.LENGTH_SHORT).show();

//                    Call<ResponsModel> getdata = api.getBiodata1();
//                    getdata.enqueue(new Callback<ResponsModel>() {
//                        @Override
//                        public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
//                            pd.hide();
                            Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                            mItems = response.body().getResult();

                            mAdapter = new adapterdata(wisataumum.this,mItems);
                            mRecycler.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
//                        }
//                        @Override
//                        public void onFailure(Call<ResponsModel> call, Throwable t) {
//                            pd.hide();
//                            Log.d("RETRO", "FAILED : respon gagal ");
//                        }
//                    });

                }
                else{
                    Toast.makeText(wisataumum.this, "Data key gagal..", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
//                pd.hide();
                Log.d("RETRO", "Falure : " + "Riquest Tidak Falid");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.boking:
               Intent boking = new Intent(wisataumum.this, com.example.application.controller.wisata.boking.class);
               startActivity(boking);
            break;
            case R.id.rute:
                Intent rute = new Intent(wisataumum.this, com.example.application.controller.wisata.rute.class);
                startActivity(rute);
                break;
            case R.id.maps:

                APISQL api = Retroserver.getClient().create(APISQL.class);
                Call<ResponsModel> getdata = api.getBiodata();
                getdata.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
//                        pd.hide();
                        mItems2 = response.body().getResult();
                        int jm = mItems2.size();
                        Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                        Bundle maps = new Bundle();
                        maps.putInt("jumlah", jm);
                        Intent map = new Intent(wisataumum.this, GisWisata.class);
                        map.putExtras(maps);
                        for(int i = 0; i<jm; i++){
                            SelectModel dm = mItems2.get(i);
                            map.putExtra("Nama_tempat"+i, dm.getNama_tempat());
                            map.putExtra("Latitude"+i, dm.getLatitude());
                            map.putExtra("Longitude"+i, dm.getLongitude());
                            map.putExtra("Jenis_Tempat"+i, dm.getJenis_Tempat());
                            map.putExtra("Gambar"+i, dm.getGambar());
                            map.putExtra("key1"+i, dm.getKeypradha2());
                        }
                        startActivity(map);
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
//                        pd.hide();
                        Log.d("RETRO", "FAILED : respon gagal ");
                    }
                });
                break;
            case R.id.logout:
                Intent logout = new Intent(wisataumum.this,login.class);
                startActivity(logout);
            break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}