package com.example.application.controller.wisata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

public class detail_user1 extends AppCompatActivity{
    TextView Key,NamaLengkap,Alamat,Umur,JenisKelamin,HP,Email;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailuser1);
        NamaLengkap = (TextView) findViewById(R.id.nama);
        Alamat = (TextView) findViewById(R.id.alamat);
        Umur = (TextView) findViewById(R.id.umur);
        JenisKelamin = (TextView) findViewById(R.id.jeniskelamin);
        HP = (TextView) findViewById(R.id.nohp);
        Email = (TextView) findViewById(R.id.email);
        pd = new ProgressDialog(this);

        Intent data = getIntent();
        NamaLengkap.setText(data.getStringExtra("Nama_Lengkap"));
        Alamat.setText(data.getStringExtra("Alamat"));
        Umur.setText(data.getStringExtra("Umur"));
        JenisKelamin.setText(data.getStringExtra("JenisKelamin"));
        HP.setText(data.getStringExtra("HP"));
        Email.setText(data.getStringExtra("Email"));
    }
    /*public void backdetail (View v){
        Intent back = new Intent(detail_user1.this, penyedia2.class);
        startActivity(back);
    }*/
}