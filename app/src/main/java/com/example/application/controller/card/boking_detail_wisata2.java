package com.example.application.controller.card;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.application.MapsActivity_Detail_Wisata;
import com.example.application.R;
import com.example.application.controller.wisata.rute;

public class boking_detail_wisata2 extends AppCompatActivity{
    TextView Key,NamaWisata,DeskripsiTempat,Waktu,Lokasi,Harga,Aturan,JenisTempat,Latitude,Longitude,Jum;
    ImageView Gambar;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailwisata);
        Key = (TextView) findViewById(R.id.key);
        NamaWisata = (TextView) findViewById(R.id.nama_tempat);
        DeskripsiTempat = (TextView) findViewById(R.id.deskripsitempat);
        Waktu = (TextView) findViewById(R.id.waktu);
        Lokasi = (TextView) findViewById(R.id.lokasi);
        Harga = (TextView) findViewById(R.id.harga);
        Aturan = (TextView) findViewById(R.id.aturan);
        JenisTempat = (TextView) findViewById(R.id.jenistempat);
        Gambar = (ImageView) findViewById(R.id.gambar);
        Latitude = (TextView) findViewById(R.id.latitude);
        Longitude = (TextView) findViewById(R.id.longitude);
        pd = new ProgressDialog(this);

        Intent data = getIntent();
        Key.setText(data.getStringExtra("keypradha2"));
        NamaWisata.setText(data.getStringExtra("Nama_tempat"));
        DeskripsiTempat.setText(data.getStringExtra("Deskripsi"));
        Waktu.setText(data.getStringExtra("Waktu"));
        Lokasi.setText(data.getStringExtra("Alamat_Lokasi"));
        Harga.setText(data.getStringExtra("Harga"));
        Aturan.setText(data.getStringExtra("Persyaratan"));
        JenisTempat.setText(data.getStringExtra("Jenis_Tempat"));
        Latitude.setText(data.getStringExtra("latitude"));
        Longitude.setText(data.getStringExtra("longitude"));
        Glide.with(this).load(data.getStringExtra("Gambar")).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(Gambar);
    }
    public void backdetail (View v){
        Intent back = new Intent(boking_detail_wisata2.this, rute.class);
        startActivity(back);
    }
    public void map (View v){
        Bundle map = new Bundle();
        map.putString("latitude", Latitude.getText().toString());
        map.putString("longitude", Longitude.getText().toString());
        map.putString("namawisata", NamaWisata.getText().toString());
        Intent mapwisata = new Intent(boking_detail_wisata2.this, MapsActivity_Detail_Wisata.class);
        mapwisata.putExtras(map);
        startActivity(mapwisata);
    }
}