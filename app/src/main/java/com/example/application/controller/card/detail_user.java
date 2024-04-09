package com.example.application.controller.card;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;
import com.example.application.controller.penyedia.penyedia2;

public class detail_user extends AppCompatActivity{
    TextView Key,NamaLengkap,Negara,Umur,JenisKelamin,HP,Email;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailuser);
        NamaLengkap = (TextView) findViewById(R.id.nama);
        Negara = (TextView) findViewById(R.id.negara);
        Umur = (TextView) findViewById(R.id.umur);
        JenisKelamin = (TextView) findViewById(R.id.jeniskelamin);
        HP = (TextView) findViewById(R.id.nohp);
        Email = (TextView) findViewById(R.id.email);
        pd = new ProgressDialog(this);

        Intent data = getIntent();
        NamaLengkap.setText(data.getStringExtra("Nama_Lengkap"));
        Negara.setText(data.getStringExtra("Negara"));
        Umur.setText(data.getStringExtra("Umur"));
        JenisKelamin.setText(data.getStringExtra("JenisKelamin"));
        HP.setText(data.getStringExtra("HP"));
        Email.setText(data.getStringExtra("Email"));
    }
    public void backdetail (View v){
        Intent back = new Intent(detail_user.this, penyedia2.class);
        startActivity(back);
    }
}