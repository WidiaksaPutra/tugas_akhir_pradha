package com.example.application.controller.map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.R;
import com.example.application.controller.wisata.wisatabudaya;
import com.example.application.model.API.APISQL;
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

public class detail_marker_budaya extends AppCompatActivity{
    TextView Key,Key1,NamaWisata,DeskripsiTempat,Waktu,Lokasi,Harga,Aturan,JenisTempat,Latitude,Longitude;
    ImageView Gambar;
    private List<SelectModel> mItems = new ArrayList<>();
    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailmarker);
        Key = (TextView) findViewById(R.id.key);
        Key1 = (TextView) findViewById(R.id.key1);
        NamaWisata = (TextView) findViewById(R.id.nama_tempat);
        DeskripsiTempat = (TextView) findViewById(R.id.deskripsitempat);
        Waktu = (TextView) findViewById(R.id.waktu);
        Lokasi = (TextView) findViewById(R.id.lokasi);
        Harga = (TextView) findViewById(R.id.harga);
        Aturan = (TextView) findViewById(R.id.aturan);
        JenisTempat = (TextView) findViewById(R.id.jenistempat);
        Gambar = (ImageView) findViewById(R.id.gambar);
        pd = new ProgressDialog(this);

        Intent data = getIntent();
        sm = new SessionManager(detail_marker_budaya.this);
        HashMap<String, String> map = sm.getDetailLogin();
        Key.setText(map.get(sm.KEY));
        NamaWisata.setText(data.getStringExtra("Nama_tempat"));
        String Nama_tempat = NamaWisata.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> cancell = api.getDetailMarker(Nama_tempat);
        cancell.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                mItems = response.body().getResult();
                if (kode.equals("1")) {
                    int jm = mItems.size();
                    for(int i = 0; i<jm; i++) {
                        SelectModel dm = mItems.get(i);
                        Key1.setText(dm.getKeypradha2());
                        DeskripsiTempat.setText(dm.getDeskripsi());
                        Waktu.setText(dm.getWaktu());
                        Lokasi.setText(dm.getWaktu());
                        Harga.setText(dm.getHarga());
                        Aturan.setText(dm.getPersyaratan());
                        JenisTempat.setText(dm.getJenis_Tempat());
                        Glide.with(detail_marker_budaya.this).load(dm.getGambar()).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Gambar);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                Log.d("RETRO", "Falure : " + "Riquest Failed");
            }
        });
    }
    public void backdetail (View v){
        Intent back = new Intent(detail_marker_budaya.this, wisatabudaya.class);
        startActivity(back);
    }
    public void Boking (View v){
        String key2 =  Key1.getText().toString();
        String key1 = Key.getText().toString();
        APISQL api = Retroserver.getClient().create(APISQL.class);
        Call<ResponsModel> boking = api.bokingTempat(key1,key2);
        boking.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {
                    Intent refresh = new Intent(detail_marker_budaya.this, wisatabudaya.class);
                    Toast.makeText(detail_marker_budaya.this, "Success Booking..", Toast.LENGTH_SHORT).show();
                    startActivity(refresh);
                }
                else if(kode.equals("3")){
                    Toast.makeText(detail_marker_budaya.this, "The data it's been booking..", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                Log.d("RETRO", "Falure : " + "Riquest Not Falid");
            }
        });
    }
}